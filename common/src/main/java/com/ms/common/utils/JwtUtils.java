package com.ms.common.utils;

import com.ms.common.constant.AuthConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {

    public static String createToken(String claim, Long expiration) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setSubject(claim)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, AuthConstant.JWT_SECRET)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(AuthConstant.JWT_SECRET).parseClaimsJws(token).getBody();
            Date expiration = claims.getExpiration();
            return expiration != null && expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public static String getSubjectFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(AuthConstant.JWT_SECRET).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
