package com.ms.user.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TokenPair implements Serializable {

    private static final long serialVersionUID = 1000L;

    String accessToken;

    String refreshToken;

    User user;

    Date accessTokenExpiration;

    Date refreshTokenExpiration;
}
