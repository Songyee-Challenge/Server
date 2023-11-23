package com.likelion.songyeechallenge.config;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final String jwtSecretKey;
    private final int jwtAccessTokenExpirationTime;
    private final int jwtRefreshTokenExpirationTime;
    private Key secretKey;

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret}") String jwtSecretKey,
                            @Value("${jwt.accessTokenExpirationTime}") int jwtAccessTokenExpirationTime,
                            @Value("${jwt.refreshTokenExpirationTime}") int jwtRefreshTokenExpirationTime) {
        this.jwtSecretKey = jwtSecretKey;
        this.jwtAccessTokenExpirationTime = jwtAccessTokenExpirationTime;
        this.jwtRefreshTokenExpirationTime = jwtRefreshTokenExpirationTime;
        this.secretKey = new SecretKeySpec(jwtSecretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }

    public String generateAccessToken(Authentication authentication) {
        log.info("jwtSecretKey: {}" , jwtSecretKey);
        log.info("jwtAccessTokenExpirationTime: {}" , jwtAccessTokenExpirationTime);
        log.info("jwtRefreshTokenExpirationTime: {}", jwtRefreshTokenExpirationTime);

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Date expiryDate = new Date(new Date().getTime() + jwtAccessTokenExpirationTime);

        Claims claims = Jwts.claims().setSubject(customUserDetails.getUsername());
        claims.put("user-id", customUserDetails.getUserId());
        claims.put("user-major", customUserDetails.getUserMajor());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
        return token;
    }

    public String generateRefreshToken(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Date expiryDate = new Date(new Date().getTime() + jwtRefreshTokenExpirationTime);
        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .claim("user-major", customUserDetails.getUserMajor())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("user-id", Long.class);
    }

    public String getUserMajorFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("user-major", String.class);
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("sub", String.class);
    }

    public String getUserEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }
}
