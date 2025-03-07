package kamenov.springkamenovnatnature.service.impl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kamenov.springkamenovnatnature.entity.UserEntity;
import kamenov.springkamenovnatnature.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    private final String jwtSecret;

    public JwtServiceImpl(@Value("${jwt.secret}")
                          String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }
    @Override
    public UserDetails extractUserFromToken(String token) {
        Claims claims = extractClaims(token);

        String userName = getUserName(claims);
        List<String> roles = getRoles(claims);

        return new User(userName, "", roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList()
        );
    }
    @Override
    public String generateToken(UserEntity user) {
        return "Bearer" + generateTokenValue(new HashMap<>(), user.getUsername());
    }
//    @SuppressWarnings("unchecked")
    @SuppressWarnings("unchecked")
    private static List<String> getRoles(Claims claims) {
        return claims.get("roles", List.class);
    }

    private static String getUserName(Claims claims) {
        return claims.getSubject();
    }

    private Claims extractClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }
    @Override
    public String generateTokenValue(Map<String, Object> claims, String username) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    }
