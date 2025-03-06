package com.juandavyc.SpringSecEx.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface JwtService {

    String getToken(UserDetails userDetails);

    String getUsernameFromToken(String token);

    Boolean isValid(String token);

    List<GrantedAuthority> getAuthorities(String token);
}
