package com.ms.user.utils;

import com.ms.common.utils.JwtUtils;
import com.ms.user.domain.entity.TokenPair;

import java.util.Date;

public class TokenUtils {

    private static final long ACCESS_TOKEN_EXPIRATION = 12 * 60 * 60 * 1000;

    private static final long REFRESH_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;

    public static TokenPair generateTokenPair(Long userId, String username) {
        String claim = userId + "_" + username;

        String accessToken = JwtUtils.createToken(claim, ACCESS_TOKEN_EXPIRATION);
        String refreshToken = JwtUtils.createToken(claim, REFRESH_TOKEN_EXPIRATION);

        TokenPair tokenPair = new TokenPair();
        tokenPair.setAccessToken(accessToken);
        tokenPair.setRefreshToken(refreshToken);
        tokenPair.setAccessTokenExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION));
        tokenPair.setRefreshTokenExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION));

        return tokenPair;
    }
}
