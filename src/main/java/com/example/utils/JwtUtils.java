package com.example.utils;

import com.example.pojo.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class JwtUtils {

    private  String signKey = "cbds";
    private  Long expire = 432000000L; // 120小时

    /**
     * 生成JWT令牌
     * @param user User实体类
     * @return JWT令牌
     */
    public  String generateJwt(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("userName", user.getUserName());
        claims.put("userPassword", user.getUserPassword());
        claims.put("userHeader", user.getUserHeader());
        claims.put("userCount", user.getUserCount());
        claims.put("userJudge",user.getUserJudge());
        String jwt = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return User实体类
     */
    public  User parseJWT(String jwt){
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(signKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }

        User user = new User();
        user.setUserId((Integer) claims.get("userId"));
        user.setUserName((String) claims.get("userName"));
        user.setUserPassword((String) claims.get("userPassword"));
        user.setUserHeader((String) claims.get("userHeader"));
        user.setUserCount((String) claims.get("userCount"));
        user.setUserJudge((Integer) claims.get("userJudge"));
        return user;
    }
}