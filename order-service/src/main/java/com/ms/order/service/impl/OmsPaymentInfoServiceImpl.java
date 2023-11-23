package com.ms.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.order.entity.PaymentInfoEntity;
import com.ms.order.mapper.OmsPaymentInfoMapper;
import com.ms.order.service.IOmsPaymentInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付信息表 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Service
public class OmsPaymentInfoServiceImpl extends ServiceImpl<OmsPaymentInfoMapper, PaymentInfoEntity> implements IOmsPaymentInfoService {

}
