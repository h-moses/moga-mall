package com.ms.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ms.warehouse.domain.entity.WmsWareSku;
import com.ms.warehouse.mapper.WmsWareSkuMapper;
import com.ms.warehouse.service.IWmsWareSkuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.warehouse.domain.vo.StockVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品库存 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
@Service
public class WmsWareSkuServiceImpl extends ServiceImpl<WmsWareSkuMapper, WmsWareSku> implements IWmsWareSkuService {

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
            stockVo.setIsStocked(stock > 0);
            return stockVo;
        }).collect(Collectors.toList());
    }
}
