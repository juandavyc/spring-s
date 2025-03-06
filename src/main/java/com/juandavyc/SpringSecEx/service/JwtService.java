package com.juandavyc.SpringSecEx.service;

import com.juandavyc.SpringSecEx.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;

public interface JwtService {

    String getToken(UserEntity user);

    String getUsernameFromToken(String token);

    boolean isTokenValid(String token);

    List<String> getRolesFromToken(String token);
    List<String> getAuthoritiesFromToken(String token);

}
