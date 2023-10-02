package com.ms.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ms.order.entity.PaymentInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 支付信息表 Mapper 接口
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Mapper
public interface OmsPaymentInfoMapper extends BaseMapper<PaymentInfoEntity> {

}
