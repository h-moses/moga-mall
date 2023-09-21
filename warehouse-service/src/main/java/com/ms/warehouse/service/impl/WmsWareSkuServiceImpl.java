package com.ms.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.common.enums.BizStatusCode;
import com.ms.common.exception.BizException;
import com.ms.common.to.StockLockTo;
import com.ms.common.to.StockTaskDetailTo;
import com.ms.warehouse.configuration.RabbitConfiguration;
import com.ms.warehouse.domain.entity.WmsWareOrderTask;
import com.ms.warehouse.domain.entity.WmsWareOrderTaskDetail;
import com.ms.warehouse.domain.entity.WmsWareSku;
import com.ms.warehouse.domain.vo.OrderItemVo;
import com.ms.warehouse.domain.vo.StockLockResVo;
import com.ms.warehouse.domain.vo.StockLockVo;
import com.ms.warehouse.domain.vo.StockVo;
import com.ms.warehouse.mapper.WmsWareSkuMapper;
import com.ms.warehouse.service.IWmsWareOrderTaskDetailService;
import com.ms.warehouse.service.IWmsWareOrderTaskService;
import com.ms.warehouse.service.IWmsWareSkuService;
import com.sun.corba.se.spi.orb.ORBData;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品库存 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */

@RabbitListener
@Service
public class WmsWareSkuServiceImpl extends ServiceImpl<WmsWareSkuMapper, WmsWareSku> implements IWmsWareSkuService {

    @Resource
    RabbitTemplate rabbitTemplate;

    @Resource
    IWmsWareOrderTaskService orderTaskService;

    @Resource
    IWmsWareOrderTaskDetailService orderTaskDetailService;

    @Override
    public Page<WmsWareSku> queryPage(Long skuId, Long wareId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<WmsWareSku> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(String.valueOf(skuId))) {
            queryWrapper.eq(WmsWareSku::getSkuId, skuId);
        }
        if (StringUtils.hasText(String.valueOf(wareId))) {
            queryWrapper.eq(WmsWareSku::getWareId, wareId);
        }
        Page<WmsWareSku> page = new Page<>(pageNum, pageSize);
        return page(page, queryWrapper);
    }

    @Override
    public List<StockVo> isStock(List<Long> skuIds) {
        return skuIds.stream().map(it -> {
            StockVo stockVo = new StockVo();
            // 如果数据库没有该skuId,会报错
            long stock = baseMapper.getStock(it);
            stockVo.setSkuId(it);
            stockVo.setHasStock(stock > 0);
            return stockVo;
        }).collect(Collectors.toList());
    }

    /**
     * 库存解锁场景
     * - 下单成功，订单过期没有支付被系统自动取消；用户手动取消
     * - 下单成功，库存锁定成功，后续业务调用失败，导致订单回滚
     *
     * @param stockLockVo
     * @return
     * @throws BizException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean lockStock(StockLockVo stockLockVo) throws BizException {

        WmsWareOrderTask orderTask = new WmsWareOrderTask();
        orderTask.setOrderSn(stockLockVo.getOrderSn());
        orderTaskService.save(orderTask);

        List<OrderItemVo> orderItemVoList = stockLockVo.getOrderItemVoList();
        List<SkuStockWare> stockWareList = orderItemVoList.stream().map(orderItemVo -> {
            SkuStockWare skuStockWare = new SkuStockWare();
            List<Long> wareIds = getBaseMapper().listWareId(orderItemVo.getSkuId());
            skuStockWare.setSkuId(orderItemVo.getSkuId());
            skuStockWare.setQuantity(orderItemVo.getCount());
            skuStockWare.setWareIds(wareIds);
            return skuStockWare;
        }).collect(Collectors.toList());
        for (SkuStockWare skuStockWare : stockWareList) {
            boolean isLocked = false;
            List<Long> wareIds = skuStockWare.getWareIds();
            if (null != wareIds && !wareIds.isEmpty()) {
                Long skuId = skuStockWare.getSkuId();
                // 锁定成功，发送一个库存的工作单给MQ
                // 锁定失败，工作单回滚。即使需要解锁库存，由于不存在该工作单，也无需解锁
                for (Long wareId : wareIds) {
                    Integer locked = getBaseMapper().lockStock(skuId, wareId, skuStockWare.getQuantity());
                    if (1 == locked) {
                        isLocked = true;
                        WmsWareOrderTaskDetail orderTaskDetail = WmsWareOrderTaskDetail.builder()
                                .skuId(skuId)
                                .skuNum(skuStockWare.getQuantity())
                                .taskId(orderTask.getId())
                                .wareId(wareId)
                                .lockStatus(1).build();
                        orderTaskDetailService.save(orderTaskDetail);
                        // 锁定成功，发送一个库存的工作单给MQ
                        StockLockTo stockLockTo = new StockLockTo();
                        StockTaskDetailTo stockTaskDetailTo = new StockTaskDetailTo();
                        BeanUtils.copyProperties(orderTaskDetail, stockTaskDetailTo);
                        stockLockTo.setId(orderTask.getId());
                        // 防止回滚找不到数据
                        stockLockTo.setDetailTo(stockTaskDetailTo);
                        rabbitTemplate.convertAndSend(RabbitConfiguration.STOCK_EXCHANGE, RabbitConfiguration.LOCK_ROUTING_KEY, stockLockTo);
                        break;
                    }
                }
                if (!isLocked) {
                    throw new BizException(BizStatusCode.INSUFFICIENT_STOCK);
                }
            }
        }
        // 全部库存锁定成功
        return Boolean.TRUE;
    }

    @Override
    public void releaseStock(StockLockTo stockLockTo, Message message) {
        StockTaskDetailTo detailTo = stockLockTo.getDetailTo();
        Long skuId = detailTo.getSkuId();

        // 解锁

    }

    @Data
    static class SkuStockWare {
        Long skuId;

        Integer quantity;

        List<Long> wareIds;
    }
}
