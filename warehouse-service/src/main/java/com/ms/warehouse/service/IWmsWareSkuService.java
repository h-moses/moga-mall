package com.ms.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.common.to.StockLockTo;
import com.ms.warehouse.domain.entity.WmsWareSku;
import com.ms.warehouse.domain.vo.StockLockResVo;
import com.ms.warehouse.domain.vo.StockLockVo;
import com.ms.warehouse.domain.vo.StockVo;
import org.springframework.amqp.core.Message;

import java.util.List;

/**
 * <p>
 * 商品库存 服务类
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
public interface IWmsWareSkuService extends IService<WmsWareSku> {

    Page<WmsWareSku> queryPage(Long skuId, Long wareId, Integer pageNum, Integer pageSize);

    List<StockVo> isStock(List<Long> skuIds);

    Boolean lockStock(StockLockVo stockLockVo);

    void releaseStock(StockLockTo stockLockTo, Message message);
}
