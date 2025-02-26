package com.juandavyc.SpringSecEx.config;


import com.juandavyc.SpringSecEx.service.MyUserDetailsService;
import com.sun.source.tree.ReturnTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfToken;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    private UserDetailsService userDetailsService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // http.csrf(customizer->customizer.disable());
        // any request only works if you are authenticated
//        http.authorizeHttpRequests(
//                auth -> auth.anyRequest().authenticated()
//        );//
//       // http.formLogin(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults());//
//        http.sessionManagement(
//                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        );


        http
                .csrf(Customizer->Customizer.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User.withDefaultPasswordEncoder()
//                .username("tara")
//                .password("t@123")
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User.withDefaultPasswordEncoder()
//                .username("noah")
//                .password("n@123")
//                .roles("ADMIN")
//                .build();
//
//
//        return new InMemoryUserDetailsManager(
//            user1, user2
//        );
//    }


    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

        provider.setUserDetailsService(myUserDetailsService);

        return provider;
    }

}
