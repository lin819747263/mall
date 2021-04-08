package com.linmsen;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static final long EXPIRE = 1000*60*60*24*7;
    private static final String TOKEN_PREFIX = "linmsen1024";
    private static final String SECRET = "linmsen6B";
    private static final String SUBJECT = "linmsen";

    /**
     * 根据用户信息，生成令牌
     *
     * @param user
     * @return
     */
    public static String geneJsonWebToken(LoginUser user) {

        Long userId = user.getId();
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("head_img", user.getHeadImg())
                .claim("id", userId)
                .claim("name", user.getName())
                .claim("mail", user.getMail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

        token = TOKEN_PREFIX + token;

        return token;
    }

    /**
     * 校验token的方法
     *
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {

        try {

            final Claims claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();

            return claims;

        } catch (Exception e) {
            return null;
        }
    }
}
