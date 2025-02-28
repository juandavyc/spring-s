package com.juandavyc.SpringSecEx.jwt;

import com.juandavyc.SpringSecEx.service.JwtService;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter /* solo se ejecute una vez */{


    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        final String username;



        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        username=jwtService.getUsernameFromToken(token);

        // no existe
        if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtService.isTokenValid(token,userDetails)){
                // actualizar security holder
                Collection<? extends GrantedAuthority> authorities = getAuthorities(token);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null,authorities
                        );
              //  authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }

    private String getTokenFromRequest(HttpServletRequest request) {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;

    }

    private Collection<? extends GrantedAuthority> getAuthorities(String token) {
        List<String> roles = jwtService.getRolesFromToken(token);
        List<String> permissions = jwtService.getPermissionsFromToken(token);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // Agregar roles con el prefijo "ROLE_"
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        // Agregar permisos sin prefijo
        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));

        System.out.println("Roles: " + jwtService.getRolesFromToken(token));
        System.out.println("Permissions: " + jwtService.getPermissionsFromToken(token));

        return authorities;
    }


}
