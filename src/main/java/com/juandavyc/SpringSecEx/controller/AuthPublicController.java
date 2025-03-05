package com.juandavyc.SpringSecEx.controller;

import com.juandavyc.SpringSecEx.dto.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/auth/public")
@RequiredArgsConstructor
public class AuthPublicController {


    @GetMapping()
    public ResponseEntity<String> getAuth(
            @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        final var fakeToken = "get auth response";
        return ResponseEntity.ok(fakeToken);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        final var fakeToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        return ResponseEntity.ok(fakeToken);
    }


}

