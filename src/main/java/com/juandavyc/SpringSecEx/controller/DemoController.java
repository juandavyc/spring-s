package com.juandavyc.SpringSecEx.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1")
@RequiredArgsConstructor
public class DemoController {

    @PostMapping
    public String welcome() {
        return "Welcome to Spring Security";
    }
}
