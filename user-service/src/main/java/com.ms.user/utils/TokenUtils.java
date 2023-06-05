package com.ms.user.utils;

import com.ms.user.entity.TokenPair;
import com.ms.user.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class TokenUtils {

    private static final long ACCESS_TOKEN_EXPIRATION = 30 * 60;

    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60;

    private static final String SECRET_KEY = "MOGA_MALL_USER_KEY";


    public static TokenPair generateTokenPair(User user) {
        String accessToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        TokenPair tokenPair = new TokenPair();
        tokenPair.setAccessToken(accessToken);
        tokenPair.setRefreshToken(refreshToken);
        tokenPair.setUser(user);
        tokenPair.setAccessTokenExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION * 1000));
        tokenPair.setRefreshTokenExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION * 1000));

        return tokenPair;
    }
}
