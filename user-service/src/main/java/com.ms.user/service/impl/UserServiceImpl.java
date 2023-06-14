package com.ms.user.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.system.UserInfo;
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
import com.ms.user.utils.UserUtils;
import com.ms.user.dto.UserInfoDto;
import com.ms.user.vo.UpdateUserInfoParamVo;
import com.ms.user.vo.UserRegisterParamVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

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

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public BizStatusCode register(UserRegisterParamVo userRegisterParamVo) throws BizException, SysException {
        if (!StringUtils.hasText(userRegisterParamVo.getUsername()) || !StringUtils.hasText(userRegisterParamVo.getPassword())) {
            throw new BizException(BizExceptionCode.PARAM_ERROR.getCode(), "用户名或密码不允许为空");
        }
        String username = userRegisterParamVo.getUsername();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        long count = count(queryWrapper);
        if (count > 0) {
            return BizStatusCode.USER_ALREADY_EXIST;
        }
        User user = new User();
        BeanUtils.copyProperties(userRegisterParamVo, user);
        user.setPassword(SecureUtil.md5(userRegisterParamVo.getPassword()));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        boolean res = save(user);
        if (res) {
            return BizStatusCode.SUCCESS;
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
        User user = getByUserName(username);
        if (null == user) {
            return BizStatusCode.USER_NOT_EXIST;
        } else if (!user.getPassword().equals(SecureUtil.md5(password))) {
            return BizStatusCode.PASSWORD_INCORRECT;
        } else {
            UserInfoDto userInfoDto = new UserInfoDto();
            BeanUtils.copyProperties(user, userInfoDto);
            userInfoDto.setGender(UserUtils.convert2String(user.getGender()));
            return userInfoDto;
        }
    }

    @Cacheable(value = "user", key = "#username")
    @Override
    public UserInfoDto queryInfo(String username) throws BizException {
        User user = getByUserName(username);
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(user, userInfoDto);
        userInfoDto.setGender(UserUtils.convert2String(user.getGender()));
        return userInfoDto;
    }

    @CachePut(value = "user", key = "#username")
    @Override
    public UserInfoDto updateInfo(String username, UpdateUserInfoParamVo updateUserInfoParamVo) throws BizException {
        User user = getByUserName(username);
        if (null == user) {
            throw new BizException(BizStatusCode.USER_NOT_EXIST);
        }
        BeanUtils.copyProperties(updateUserInfoParamVo, user);
        user.setGender(UserUtils.convert2Code(updateUserInfoParamVo.getGender()));
        user.setUpdateTime(new Date());
        boolean res = updateById(user);
        if (res) {
            UserInfoDto userInfoDto = new UserInfoDto();
            BeanUtils.copyProperties(user, userInfoDto);
            userInfoDto.setGender(UserUtils.convert2String(user.getGender()));
            return userInfoDto;
        } else {
            throw new SysException(SysExceptionCode.UPDATE_DB_ERROR);
        }
    }

    private User getByUserName(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return getOne(queryWrapper);
    }
}
