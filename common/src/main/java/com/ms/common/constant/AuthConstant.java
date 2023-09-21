package com.ms.common.constant;

public interface AuthConstant {

    // JWT密钥
    String JWT_SECRET = "MOGA_MALL_USER";

    // JWT访问令牌过期时间
    String JWT_ACCESS_EXPIRATION = "86400";

    // JWT刷新令牌过期时间
    String JWT_REFRESH_EXPIRATION = "100000";

    // JWT前缀
    String JWT_PREFIX = "Bearer ";

    String JWT_HEADER = "Authorization";

//    String USERID_HEADER = "userId";

    String USER_HEADER = "username";
}
