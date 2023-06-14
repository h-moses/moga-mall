package com.ms.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ms.common.enums.BizStatusCode;
import com.ms.common.exception.BizException;
import com.ms.common.exception.SysException;
import com.ms.user.dto.UserInfoDto;
import com.ms.user.entity.User;
import com.ms.user.vo.UpdateUserInfoParamVo;
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

    BizStatusCode register(UserRegisterParamVo userRegisterParamVo) throws BizException, SysException;

    Object login(String username, String password) throws BizException;

    UserInfoDto queryInfo(String username) throws BizException;

    UserInfoDto updateInfo(String username, UpdateUserInfoParamVo updateUserInfoParamVo) throws BizException;
}
