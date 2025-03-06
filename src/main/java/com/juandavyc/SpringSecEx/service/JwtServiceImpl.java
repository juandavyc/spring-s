package com.juandavyc.SpringSecEx.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final String secretKey;

    public JwtServiceImpl() {

        try {

            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            secretKey = Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public String getToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        Set<String> roles = new HashSet<>();
        Set<String> authorities = new HashSet<>();

        userDetails.getAuthorities()
                .forEach(authority -> {
                    if (authority.getAuthority().startsWith("ROLE_")) {
                        roles.add(authority.getAuthority());
                    } else {
                        authorities.add(authority.getAuthority());
                    }
                });

        claims.put("roles", roles);
        claims.put("authorities", authorities);


        return Jwts.builder()
                .signWith(getSecretKey())
                .subject(userDetails.getUsername())
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 minutes
                .compact();

    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @Override
    public Boolean isValid(String token) {
        return !isExpired(token);
    }

    @Override
    public List<GrantedAuthority> getAuthorities(String token) {
        final var claims = getClaimsFromToken(token);

        Set<String> authoritiesSet = new HashSet<>();
        List<String> roles = claims.get("roles", List.class);
        List<String> authorities = claims.get("authorities", List.class);

        authoritiesSet.addAll(roles);
        authoritiesSet.addAll(authorities);

        return authoritiesSet.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    private SecretKey getSecretKey() {
        byte[] encodedKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(encodedKey);
    }

    // claims

    private Boolean isExpired(String token) {
        return getExpirationFromToken(token).before(new Date());
    }

    private Date getExpirationFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Jws<Claims> isSigned(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            throw new IllegalStateException("JWT token is not signed");
        }
    }

    private Claims getClaimsFromToken(String token) {
        return isSigned(token).getPayload();
    }



    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

}
