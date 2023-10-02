package com.ms.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.common.to.OrderTo;
import com.ms.common.to.StockLockTo;
import com.ms.warehouse.domain.entity.WareSkuEntity;
import com.ms.warehouse.domain.vo.StockLockVo;
import com.ms.warehouse.domain.vo.StockVo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 商品库存 服务类
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
public interface IWmsWareSkuService extends IService<WareSkuEntity> {

    Page<WareSkuEntity> queryPage(Long skuId, Long wareId, Integer pageNum, Integer pageSize);

    List<StockVo> isStock(List<Long> skuIds);

    Boolean lockStock(StockLockVo stockLockVo);

    void releaseStock(StockLockTo stockLockTo, Message message, Channel channel) throws IOException;

    void releaseStockOnOrderClosed(OrderTo orderTo, Message message);
}
