package com.juandavyc.SpringSecEx.service;

import com.juandavyc.SpringSecEx.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final String secretKey;

    public JwtServiceImpl() {
        // create the secret key dynamic
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            secretKey = Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getToken(UserEntity user) {

        Map<String, Object> claims = new HashMap<>();
        // TODO: create bean
        Set<String> roles = user.getRoles()
                .stream()
                .map(roleEntity -> roleEntity.getName().name())
                .collect(Collectors.toSet());


        Set<String> permissions = user.getRoles()
                .stream()
                .flatMap(roleEntity -> roleEntity.getPermissions().stream())
                .map(permissionEntity -> permissionEntity.getName().name())
                .collect(Collectors.toSet());

        claims.put("roles", roles);
        claims.put("authorities", permissions);

        return Jwts.builder()
                .subject(user.getUsername())
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 2 * 60 * 1000))
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token) {
      return !isTokenExpired(token);
    }

    @Override
    public List<String> getRolesFromToken(String token) {

        final var claims = getClaimsFromToken(token);
        // System.out.println(claims.get("roles"));
        return claims.get("roles", List.class);

    }

    @Override
    public List<String> getAuthoritiesFromToken(String token) {
        final var claims = getClaimsFromToken(token);
        return claims.get("authorities", List.class);
    }

    // internal service

    private Date getExpirationToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);

    }

    private boolean isTokenExpired(String token) {
        return getExpirationToken(token).before(new Date());
    }

    private SecretKey getSecretKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    private Jws<Claims> isSignedToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            throw new IllegalStateException("token is not signed");
        }
    }


    private Claims getClaimsFromToken(String token) {
        return isSignedToken(token).getPayload();
    }


    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


}
