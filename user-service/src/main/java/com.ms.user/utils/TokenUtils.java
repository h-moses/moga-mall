package com.ms.user.utils;

import com.ms.common.utils.JwtUtils;
import com.ms.user.entity.TokenPair;
import com.ms.user.entity.User;

import java.util.Date;

public class TokenUtils {

    private static final long ACCESS_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;

    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000;

    public static TokenPair generateTokenPair(User user) {
        String accessToken = JwtUtils.createToken(user.getUsername(), ACCESS_TOKEN_EXPIRATION);

        String refreshToken = JwtUtils.createToken(user.getUsername(), REFRESH_TOKEN_EXPIRATION);

        TokenPair tokenPair = new TokenPair();
        tokenPair.setAccessToken(accessToken);
        tokenPair.setRefreshToken(refreshToken);
        tokenPair.setUser(user);
        tokenPair.setAccessTokenExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION * 1000));
        tokenPair.setRefreshTokenExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION * 1000));

        return tokenPair;
    }
}
