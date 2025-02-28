package com.juandavyc.SpringSecEx.service;

import com.juandavyc.SpringSecEx.auth.AuthResponse;
import com.juandavyc.SpringSecEx.dto.LoginRequest;
import com.juandavyc.SpringSecEx.dto.RegisterRequest;

import com.juandavyc.SpringSecEx.entity.user.Role;
import com.juandavyc.SpringSecEx.entity.user.RoleEntity;
import com.juandavyc.SpringSecEx.entity.user.UserEntity;
import com.juandavyc.SpringSecEx.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    // remplazar por el service
    private final UserRepository userRepository;

    private final JwtServiceImpl jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final RoleService roleService;


    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );


        final var user = userRepository.findByUsername(request.getUsername());
        System.out.println(user);
        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        // mapper

        Set<RoleEntity> roles = new HashSet<>();
        request.getRoles().forEach(role -> {
            roles.add(roleService.findById(role));
        });

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .country(request.getCountry())
                .roles(roles)
                .build();

        final var userCreated = userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
