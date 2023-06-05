package com.ms.user.service;

import com.ms.common.exception.BizException;
import com.ms.common.exception.SysException;
import com.ms.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.user.vo.UserRegisterParamVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ms
 * @since 2023-06-03
 */
public interface IUserService extends IService<User> {

    Object register(UserRegisterParamVo userRegisterParamVo) throws BizException, SysException;

    Object login(String username, String password) throws BizException;
}
