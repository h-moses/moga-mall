package com.ms.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ms.common.utils.BeanUtils;
import com.ms.user.domain.vo.UserAddressVo;
import com.ms.user.service.IUmsDeliveryAddressService;
import com.ms.user.domain.entity.UmsDeliveryAddress;
import com.ms.user.mapper.UmsDeliveryAddressMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员收货地址 服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-09-08
 */
@Service
public class UmsDeliveryAddressServiceImpl extends ServiceImpl<UmsDeliveryAddressMapper, UmsDeliveryAddress> implements IUmsDeliveryAddressService {

    @Override
    public List<UserAddressVo> queryAddress(Long userId) {
        LambdaQueryWrapper<UmsDeliveryAddress> wrapper = new LambdaQueryWrapper<UmsDeliveryAddress>().eq(UmsDeliveryAddress::getUserId, userId);
        List<UmsDeliveryAddress> addressList = list(wrapper);
        return BeanUtils.copyList(addressList, UserAddressVo.class);
    }

    @Override
    public UserAddressVo queryAddressById(Long addressId) {
        UmsDeliveryAddress address = getById(addressId);
        UserAddressVo userAddressVo = new UserAddressVo();
        if (null != address) {
            org.springframework.beans.BeanUtils.copyProperties(address, userAddressVo);
            return userAddressVo;
        }
        return userAddressVo;
    }
}
