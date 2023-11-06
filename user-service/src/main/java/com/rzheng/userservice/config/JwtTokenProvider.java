package com.rzheng.userservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author Richard
 */
@Slf4j
public class JwtTokenProvider {

    public static final String ISSUER = "rzheng.com";

    private final SecretKey signingKey;
    private final int expirationInMs;

    public JwtTokenProvider(String secretKey, int expirationInMs) {
        this.expirationInMs = expirationInMs;
        this.signingKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationInMs);

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .issuer(ISSUER)
                .expiration(expiryDate)
                .signWith(this.signingKey)
                .compact();
    }

    public String getEmailFromJwt(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(this.signingKey)
                .requireIssuer(ISSUER)
                .build()
                .parseSignedClaims(token);

        return claimsJws.getPayload().getSubject();
    }

    public void validateToken(String token) {
        Jwts.parser()
                .verifyWith(this.signingKey)
                .build()
                .parseSignedClaims(token);
    }
}