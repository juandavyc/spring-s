package com.juandavyc.SpringSecEx.service;

import com.juandavyc.SpringSecEx.auth.AuthResponse;
import com.juandavyc.SpringSecEx.dto.LoginRequest;
import com.juandavyc.SpringSecEx.dto.RegisterRequest;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse register(RegisterRequest registerRequest);

}
