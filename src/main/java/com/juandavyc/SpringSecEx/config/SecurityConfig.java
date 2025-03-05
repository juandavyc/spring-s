package com.juandavyc.SpringSecEx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(csrf -> csrf.disable())
                .formLogin(login -> login.disable())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                   authorize.requestMatchers("/api/auth/public/**").permitAll();
                   authorize.requestMatchers("/api/students/**").hasAnyRole("ADMIN", "USER");
                   authorize.anyRequest().authenticated();
                })
                .build();

    }

}
