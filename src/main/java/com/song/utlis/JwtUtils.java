package com.song.utlis;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static String signKey = "asdhiusahdjianshkljdnkalskaiosdioasjiodsasadsadasdsawqewq";
    private static Long expire = 43200000L;

    /**
     * 生成JWT令牌
     *
     * @param claims JWT第二部分负载 payload 中存储的内容
     * @return
     */
    public static String generateJwt(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }

    /**
     * 解析JWT令牌
     *
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

    /**
     * JWT解析为Map
     *
     * @param jwt
     * @return
     */
    public static Map<String, Object> jwtToMap(String jwt) {
        // 解析JWT
        Claims claims = Jwts.parser()
                .setSigningKey(signKey.getBytes())
                .parseClaimsJws(jwt)
                .getBody();

        // Claims对象本身就是一个Map
        return new HashMap<>(claims);
    }

    /**
     * string转jwt
     */
    public static String generateJwtWithSubject(String subject) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expire);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .compact();
    }

}
