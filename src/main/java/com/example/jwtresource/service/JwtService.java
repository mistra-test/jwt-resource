package com.example.jwtresource.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    private SecretKey secretKey;

    public JwtService() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public User decodeToken(String token) {
        var claims = extractAllClaims(token);
        var user = new User();
        user.setName(claims.getSubject());
        user.setRoles(Arrays.asList(claims.get("roles", String.class).split(";")));
        return user;
    }

    public String encodeUser(User userDetails) {
        Map<String, Object> map = new HashMap<>();
        map.put("roles", userDetails.getRoles().stream().collect(Collectors.joining(";")));
        return createToken(map, userDetails.getName());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final var claims = extractAllClaims(token);
        claims.getIssuer();
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, User userDetails) {
        final String userName = extractUserName(token);
        return (userDetails.getName().equals(userName) && !isTokenExpired(token));
    }
}
