package com.juandavyc.SpringSecEx.jwt;

import com.juandavyc.SpringSecEx.service.JwtService;
import com.juandavyc.SpringSecEx.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.SecondaryTable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // obtiene token de la solicitud header
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {

            // verificar que tenga formato correcto
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            } else {
                filterChain.doFilter(request, response);
                return;
            }
            // extraer username
            String username = jwtService.getUsernameFromToken(token);

            // verificar si ya esta autenticado
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // validar token y configurar autenticacion
                if (jwtService.isTokenValid(token)) {

                    // extraer propiedades del token
                    final var authorities = getAuthorities(token);
                    //


                    // crea un userpassword con el usuario autentiocado
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    new User(username, "", authorities),
                                    null,
                                    authorities);
                    //
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // alamacena la autenticacion en security holder
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }

        } catch (Exception e) {
            throw new IllegalStateException("Token inválido o expirado");
        }
        // continua el filtro
        filterChain.doFilter(request, response);
    }

    private List<SimpleGrantedAuthority> getAuthorities(String token) {
        List<String> roles = jwtService.getRolesFromToken(token);
        List<String> authorities = jwtService.getAuthoritiesFromToken(token);

        //System.out.println("roles: " + roles);
        //System.out.println("authorities: " + authorities);

        List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();

        authoritiesList.addAll(roles.stream().map(
                role -> new SimpleGrantedAuthority("ROLE_".concat(role))
        ).toList());

        authoritiesList.addAll(authorities.stream().map(
                authority -> new SimpleGrantedAuthority(authority)
        ).toList());

        return authoritiesList;
    }


}
