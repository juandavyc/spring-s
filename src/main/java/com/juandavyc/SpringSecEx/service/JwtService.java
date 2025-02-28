package com.juandavyc.SpringSecEx.service;

import com.juandavyc.SpringSecEx.entity.user.UserEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface JwtService {

    String getToken(UserEntity user);

    boolean isTokenValid(String token, UserDetails userDetails);

    String getUsernameFromToken(String token);

    List<String> getRolesFromToken(String token);

    List<String> getPermissionsFromToken(String token);

}
