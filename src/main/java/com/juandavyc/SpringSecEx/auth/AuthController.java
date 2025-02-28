package com.juandavyc.SpringSecEx.auth;

import com.juandavyc.SpringSecEx.dto.LoginRequest;
import com.juandavyc.SpringSecEx.dto.RegisterRequest;
import com.juandavyc.SpringSecEx.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping(path = "register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

}
