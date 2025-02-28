package com.juandavyc.SpringSecEx.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@RequiredArgsConstructor

public class DemoController {

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE')")
    public String create() {
        return "Welcome to CREATE Spring Security";
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public String read() {
        return "Welcome to READ Spring Security";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE')")
    public String update() {
        return "Welcome to UPDATE Spring Security";
    }


}
