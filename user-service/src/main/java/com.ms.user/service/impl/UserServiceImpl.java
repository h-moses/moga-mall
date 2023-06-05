package com.ms.user.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ms.common.enums.BizExceptionCode;
import com.ms.common.enums.BizStatusCode;
import com.ms.common.enums.SysExceptionCode;
import com.ms.common.exception.BizException;
import com.ms.common.exception.SysException;
import com.ms.user.entity.User;
import com.ms.user.mapper.UserMapper;
import com.ms.user.service.IUserService;
import com.ms.user.utils.TokenUtils;
import com.ms.user.vo.UserRegisterParamVo;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-06-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final String JWT_KEY = "802324";

    @Override
    public Object register(UserRegisterParamVo userRegisterParamVo) throws BizException, SysException {
        if (!StringUtils.hasText(userRegisterParamVo.getUsername()) || !StringUtils.hasText(userRegisterParamVo.getPassword())) {
            throw new BizException(BizExceptionCode.PARAM_ERROR.getCode(), "用户名或密码不允许为空");
        }
        String username = userRegisterParamVo.getUsername();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        long count = count(queryWrapper);
        if (count > 0) {
            return BizStatusCode.USER_ALREADY_EXIST.getMessage();
        }
        User user = new User();
        BeanUtils.copyProperties(userRegisterParamVo, user);
        user.setPassword(SecureUtil.md5(userRegisterParamVo.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        boolean res = save(user);
        if (res) {
            return BizStatusCode.SUCCESS.getMessage();
        } else {
            throw new SysException(SysExceptionCode.SAVE_DB_ERROR.getCode(), "用户注册失败，请稍后再试！");
        }
    }

    @CachePut(value = "user", key = "#username")
    @Override
    public Object login(String username, String password) throws BizException {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new BizException(BizExceptionCode.PARAM_ERROR.getCode(), "用户名或密码不允许为空");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = getOne(queryWrapper);
        if (null == user) {
            return BizStatusCode.USER_NOT_EXIST;
        } else if (!user.getPassword().equals(SecureUtil.md5(password))) {
            return BizStatusCode.PASSWORD_INCORRECT;
        } else {
            return TokenUtils.generateTokenPair(user);
        }
    }
}
