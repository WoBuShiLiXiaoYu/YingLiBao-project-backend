package com.ylb.work.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.time.DateUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JWTUtil {

    private String key;

    public JWTUtil(String key) {
        this.key = key;
    }

    public String createJWT(Map<String, Object> data, Integer minute) throws Exception {
        Date createDate = new Date();

        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder().signWith(secretKey, SignatureAlgorithm.HS256)// 设置签名
                // 设置过期时间
                .setExpiration(DateUtils.addMinutes(createDate, minute))
                // 签发时间
                .setIssuedAt(createDate)
                // id
                .setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase())
                .addClaims(data)
                .compact();

        return jwt;
    }

    public Claims readJWT(String jwt) throws Exception {
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        Claims body = Jwts.parserBuilder().setSigningKey(secretKey)
                .build().parseClaimsJws(jwt).getBody();
        return body;
    }
}
