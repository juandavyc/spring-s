package com.juandavyc.SpringSecEx.service;

import com.juandavyc.SpringSecEx.dto.LoginRequestDTO;
import com.juandavyc.SpringSecEx.dto.LoginResponseDTO;
import com.juandavyc.SpringSecEx.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        // System.out.println(loginRequestDTO);
        // validate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );

        final var userEntity = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = jwtService.getToken(userEntity);

        return LoginResponseDTO
                .builder()
                .token(token)
                .build();
    }
}
