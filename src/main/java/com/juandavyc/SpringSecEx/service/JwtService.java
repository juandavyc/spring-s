package com.juandavyc.SpringSecEx.service;

import com.juandavyc.SpringSecEx.entity.user.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String getToken(UserEntity user);

    boolean isTokenValid(String token, UserDetails userDetails);

    String getUsernameFromToken(String token);
}
