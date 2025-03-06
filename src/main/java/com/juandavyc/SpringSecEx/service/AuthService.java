package com.juandavyc.SpringSecEx.service;

import com.juandavyc.SpringSecEx.dto.LoginRequestDTO;
import com.juandavyc.SpringSecEx.dto.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO request);

}
