package com.ms.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.user.domain.entity.UmsDeliveryAddress;
import com.ms.user.domain.vo.UserAddressVo;

import java.util.List;

/**
 * <p>
 * 会员收货地址 服务类
 * </p>
 *
 * @author ms
 * @since 2023-09-08
 */
public interface IUmsDeliveryAddressService extends IService<UmsDeliveryAddress> {

    List<UserAddressVo> queryAddress(Long userId);

    UserAddressVo queryAddressById(Long addressId);
}
