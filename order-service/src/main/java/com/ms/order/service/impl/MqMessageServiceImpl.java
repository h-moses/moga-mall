package com.ms.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.order.entity.RabbitMessageEntity;
import com.ms.order.mapper.MqMessageMapper;
import com.ms.order.service.IMqMessageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Service
public class MqMessageServiceImpl extends ServiceImpl<MqMessageMapper, RabbitMessageEntity> implements IMqMessageService {

}
