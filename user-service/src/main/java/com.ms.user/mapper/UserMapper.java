package com.ms.user.mapper;

import com.ms.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ms
 * @since 2023-06-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
