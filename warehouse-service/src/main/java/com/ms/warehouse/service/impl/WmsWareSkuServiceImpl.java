package com.ms.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.common.api.Response;
import com.ms.common.constant.AuthConstant;
import com.ms.common.enums.BizStatusCode;
import com.ms.common.enums.OrderStatus;
import com.ms.common.exception.BizException;
import com.ms.common.to.OrderTo;
import com.ms.common.to.StockLockTo;
import com.ms.common.to.StockTaskDetailTo;
import com.ms.warehouse.configuration.RabbitConfiguration;
import com.ms.warehouse.domain.entity.WareOrderTaskDetailEntity;
import com.ms.warehouse.domain.entity.WareSkuEntity;
import com.ms.warehouse.domain.entity.WmsWareOrderTask;
import com.ms.warehouse.domain.vo.OrderItemVo;
import com.ms.warehouse.domain.vo.OrderVo;
import com.ms.warehouse.domain.vo.StockLockVo;
import com.ms.warehouse.domain.vo.StockVo;
import com.ms.warehouse.feign.OrderServiceFeign;
import com.ms.warehouse.interceptor.WarehouseInterceptor;
import com.ms.warehouse.mapper.WmsWareSkuMapper;
import com.ms.warehouse.service.IWmsWareOrderTaskDetailService;
import com.ms.warehouse.service.IWmsWareOrderTaskService;
import com.ms.warehouse.service.IWmsWareSkuService;
import com.rabbitmq.client.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.ms.warehouse.configuration.RabbitConfiguration.STOCK_RELEASE_QUEUE;

/**
 * <p>
 * 商品库存 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */

@Slf4j
@RabbitListener(queues = STOCK_RELEASE_QUEUE)
@Service
public class WmsWareSkuServiceImpl extends ServiceImpl<WmsWareSkuMapper, WareSkuEntity> implements IWmsWareSkuService {

    @Resource
    RabbitTemplate rabbitTemplate;

    @Resource
    IWmsWareOrderTaskService orderTaskService;

    @Resource
    IWmsWareOrderTaskDetailService orderTaskDetailService;

    @Resource
    OrderServiceFeign orderServiceFeign;

    @Override
    public Page<WareSkuEntity> queryPage(Long skuId, Long wareId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<WareSkuEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(String.valueOf(skuId))) {
            queryWrapper.eq(WareSkuEntity::getSkuId, skuId);
        }
        if (StringUtils.hasText(String.valueOf(wareId))) {
            queryWrapper.eq(WareSkuEntity::getWareId, wareId);
        }
        Page<WareSkuEntity> page = new Page<>(pageNum, pageSize);
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
        log.info(Thread.currentThread().getId() + "锁定库存");
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
                        WareOrderTaskDetailEntity orderTaskDetail = WareOrderTaskDetailEntity.builder()
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
                        rabbitTemplate.convertAndSend(RabbitConfiguration.STOCK_EXCHANGE, RabbitConfiguration.LOCK_ROUTING_KEY, stockLockTo, message -> {
                            message.getMessageProperties().getHeaders().put(AuthConstant.USER_HEADER, WarehouseInterceptor.THREAD_LOCAL_STOCK.get());
                            return message;
                        });
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

    /**
     * 库存自动解锁
     *     下单成功，库存锁定成功，接下来的业务调用失败，导致订单回滚。之前锁定的库存就要自动解锁
     * 订单失败，锁库存失败
     * @param stockLockTo
     * @param message
     */
    @RabbitHandler
    @Override
    public void releaseStock(StockLockTo stockLockTo, Message message, Channel channel) throws IOException {
        log.warn(Thread.currentThread().getId() + "收到解锁库存的消息");
        StockTaskDetailTo detailTo = stockLockTo.getDetailTo();
        Long detailId = detailTo.getId();

        // 如果存在库存工作单，需要解锁
        // 如果锁定成功，但不存在订单，必须解锁
        // 如果锁定成功，且存在该订单，判断订单状态是否是取消状态
        WareOrderTaskDetailEntity orderTaskDetail = orderTaskDetailService.getById(detailId);
        if (null != orderTaskDetail) {
            Long lockToId = stockLockTo.getId();
            WmsWareOrderTask orderTask = orderTaskService.getById(lockToId);
            String orderSn = orderTask.getOrderSn();
            Response<OrderVo> orderStatusResponse = orderServiceFeign.queryOrderInfo(orderSn);
            if (null != orderStatusResponse && 200 == orderStatusResponse.getCode()) {
                OrderVo orderVo = orderStatusResponse.getData();
                if (null == orderVo || OrderStatus.getByCode(orderVo.getStatus()).isCancelled()) {
                    // 处于锁定状态才能解锁
                    if (1 == orderTaskDetail.getLockStatus()) {
                        // 订单已经取消
                        handleReleaseStock(detailTo.getSkuId(), detailTo.getWareId(), detailTo.getSkuNum(), detailId);
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                        log.info("库存解锁成功");
                    }
                }
            } else {
                // 拒绝消息，重新放到队列，留待后续处理
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            }
        } else {
            log.error("该工作单不存在");
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    /**
     * 支付成功，扣减相应库存
     * @param orderSn 订单编号
     */
    @Transactional
    @Override
    public void deductStock(String orderSn) {
        // 根据订单编号，找到工作单
        WmsWareOrderTask orderTask = orderTaskService.queryTaskByOrderSn(orderSn);

        // 根据工作单id，找到工作单细则
        List<WareOrderTaskDetailEntity> detailList = orderTaskDetailService.list(new LambdaQueryWrapper<WareOrderTaskDetailEntity>().eq(WareOrderTaskDetailEntity::getTaskId, orderTask.getId()));

        // 根据细则，先检查工作单状态，扣除商品在特定仓库的库存, 并修改状态
        for (WareOrderTaskDetailEntity taskDetail : detailList) {
            if (1 == taskDetail.getLockStatus()) {
                // 扣减库存
                getBaseMapper().deductStock(taskDetail.getSkuId(), taskDetail.getWareId(), taskDetail.getSkuNum());
                // 修改库存状态
                taskDetail.setLockStatus(3);
                orderTaskDetailService.updateById(taskDetail);
            } else {
                throw new BizException(BizStatusCode.GOODS_INVALID.getCode(), "编号：" +  taskDetail.getSkuId() + "的商品库存未处于锁定状态");
            }
        }
    }

    /**
     * 因订单关闭导致的库存解锁
     */
    @RabbitHandler
    @Override
    public void releaseStockOnOrderClosed(OrderTo orderTo, Message message) {
        String orderSn = orderTo.getOrderSn();

        // 确认库存解锁状态
        //     1. 先查找库存工作单
        WmsWareOrderTask orderTask = orderTaskService.queryTaskByOrderSn(orderSn);

        // 找到工作单对应的未解锁的库存
        List<WareOrderTaskDetailEntity> detailEntityList = orderTaskDetailService.list(new LambdaQueryWrapper<WareOrderTaskDetailEntity>()
                .eq(WareOrderTaskDetailEntity::getTaskId, orderTask.getId())
                .eq(WareOrderTaskDetailEntity::getLockStatus, 1));
        for (WareOrderTaskDetailEntity orderTaskDetail : detailEntityList) {
            handleReleaseStock(orderTaskDetail.getSkuId(), orderTaskDetail.getWareId(), orderTaskDetail.getSkuNum(), orderTaskDetail.getId());
        }
    }

    private void handleReleaseStock(Long skuId, Long wareId, Integer skuNum, Long detailId) {
        getBaseMapper().releaseStock(skuId, wareId, skuNum);
        WareOrderTaskDetailEntity orderTaskDetail = WareOrderTaskDetailEntity.builder()
                .id(detailId)
                .lockStatus(2)
                .build();
        orderTaskDetailService.updateById(orderTaskDetail);
    }

    @Data
    static class SkuStockWare {
        Long skuId;

        Integer quantity;

        List<Long> wareIds;
    }
}
