package com.ms.user.utils;

import com.ms.common.utils.JwtUtils;
import com.ms.user.entity.TokenPair;
import com.ms.user.entity.User;

import java.util.Date;

public class TokenUtils {

    private static final long ACCESS_TOKEN_EXPIRATION = 12 * 60 * 60 * 1000;

    private static final long REFRESH_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;

    public static TokenPair generateTokenPair(String username) {
        String accessToken = JwtUtils.createToken(username, ACCESS_TOKEN_EXPIRATION);

        String refreshToken = JwtUtils.createToken(username, REFRESH_TOKEN_EXPIRATION);

        TokenPair tokenPair = new TokenPair();
        tokenPair.setAccessToken(accessToken);
        tokenPair.setRefreshToken(refreshToken);
        tokenPair.setAccessTokenExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION));
        tokenPair.setRefreshTokenExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION));

        return tokenPair;
    }
}
