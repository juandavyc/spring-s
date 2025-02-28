package com.juandavyc.SpringSecEx.service;

import com.juandavyc.SpringSecEx.entity.user.UserEntity;
import io.jsonwebtoken.Claims;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private String secretKey = "";

    public JwtServiceImpl() {
        try {

            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sc = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sc.getEncoded());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public String getToken(UserEntity user /*UserDetails*/) {

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(Instant.now().toEpochMilli()))
                .expiration(new Date(Instant.now().toEpochMilli() * 60 * 30))
                .signWith(getKey())
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = userDetails.getUsername();
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    //    private Key getKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Decodifica la clave Base64
        return Keys.hmacShaKeyFor(keyBytes); // Crea una SecretKey
    }

    private Claims getAllClaims(String token) {
        SecretKey key = getKey();
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token) // Usa parseSignedClaims en lugar de parseClaimsJws
                .getPayload();


    }

    public <T> T getClaim(String token, Function<Claims, T> claims) {
        final Claims claim = getAllClaims(token);
        return claims.apply(claim);
    }

    public Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }
    private Boolean isTokenExpired(String token) {
       return getExpirationDate(token).before(Date.from(Instant.now()));
    }
}
