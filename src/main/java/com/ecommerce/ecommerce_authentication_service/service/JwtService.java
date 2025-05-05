package com.ecommerce.ecommerce_authentication_service.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
    private final String SECRET = "5VMSl8KZWmsPKkoMrky3UgoKNLKPPctUMm3tzr9aXw3h1D0HK2WJgy8EtoCbshREP+l0a4OCRat7xrMU20aJ+2jsQojn/FRTYD9cgiGUvHQyPqVG/pKivcLv0rWF4Z3tyAguBREv9QslplYDrGR2U+uznH3oLvnDgKGOraOGrw7XZ5w6/awGBgYOvZR4VwPDEpW7jk/js1Vbj62YvVuGGM4H3E6e8ujzN98lHP43LZED71NJlbi+xyENICMftchhcCiH2KWa3yV0hswVghMrtdo0eVOK9dpNIzaBWXNGzsf/ZT7qixBQMp0Qzypsd7CDtBv6utOd/jBmHisFiJZ+3txgCPe5lHmV3f+uZflgJXg=";

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userName)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean validateToken(String token) {
        final String username = extractUsername(token);
        return (!username.isEmpty() && !isTokenExpired(token));
    }
    
}
