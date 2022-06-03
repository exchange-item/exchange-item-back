package com.bakooza.bakooza.Service;

import com.bakooza.bakooza.Domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtils {

    @Value("${my.jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${my.jwt.refreshKey}")
    private String REFRESH_KEY;
    public String createJwt(Member member) {

        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        String subject = member.getId().toString();

        Long expiredTime = 1000 * 60L * 60L * 2L;

        Date date = new Date();
        date.setTime(date.getTime() + expiredTime);

        SecretKey signingKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder()
                .setHeader(headers)
                .setSubject(subject)
                .setIssuer("exchange")
                .setExpiration(date)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    public String createRefreshToken(Member member) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        String subject = member.getId().toString();

        Long expiredTime = 1000 * 60L * 60L * 24L * 14L;

        Date date = new Date();
        date.setTime(date.getTime() + expiredTime);

        SecretKey signingKey = Keys.hmacShaKeyFor(REFRESH_KEY.getBytes(StandardCharsets.UTF_8));

        String refreshToken = Jwts.builder()
                .setHeader(headers)
                .setSubject(subject)
                .setIssuer("itmo")
                .setExpiration(date)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();

        return refreshToken;
    }

    private Claims extractClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    public boolean validateToken(String token) throws ExpiredJwtException {
        try {
            return true;
        } catch (ExpiredJwtException e) {
            // 토큰 만료되었을 경우
            System.out.println(e);
            return false;
        } catch (Exception e) {
            // 나머지 에러
            System.out.println(e);
            return false;
        }
    }

    public String getIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
