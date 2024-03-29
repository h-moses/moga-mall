package com.ms.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.warehouse.domain.entity.WmsWareOrderTask;
import com.ms.warehouse.mapper.WmsWareOrderTaskMapper;
import com.ms.warehouse.service.IWmsWareOrderTaskService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 库存工作单 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
@Service
public class WmsWareOrderTaskServiceImpl extends ServiceImpl<WmsWareOrderTaskMapper, WmsWareOrderTask> implements IWmsWareOrderTaskService {

    @Override
    public WmsWareOrderTask queryTaskByOrderSn(String orderSn) {
        return getOne(new LambdaQueryWrapper<WmsWareOrderTask>().eq(WmsWareOrderTask::getOrderSn,orderSn));
    }
}
