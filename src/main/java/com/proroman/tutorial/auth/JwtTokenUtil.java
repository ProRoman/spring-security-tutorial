package com.proroman.tutorial.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private static final int TOKEN_VALIDITY_SECONDS = 3600;
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(TokenRequest user) {
        return internalGenerateToken(user.getUsername());
    }

    private String internalGenerateToken(String subject) {

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", Collections.singletonList("User"));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
