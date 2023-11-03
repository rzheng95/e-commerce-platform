package com.rzheng.userservice.config;

import com.rzheng.userservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author Richard
 */
@Slf4j
public class JwtTokenProvider {

    private final SecretKey signingKey;
    private final int expirationInMs;

    public JwtTokenProvider(String secretKey, int expirationInMs) {
        this.expirationInMs = expirationInMs;
        this.signingKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationInMs);

        return Jwts.builder()
                .subject(userPrincipal.getEmail())
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(this.signingKey)
                .compact();
    }

    public String getEmailFromJwt(String token) {

        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(this.signingKey)
                .requireIssuer("rzheng.com")
                .build()
                .parseSignedClaims(token);

        return claimsJws.getPayload().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(this.signingKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}