package com.ms.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ms.order.entity.RabbitMessageEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ms
 * @since 2023-09-07
 */
@Mapper
public interface MqMessageMapper extends BaseMapper<RabbitMessageEntity> {

}
