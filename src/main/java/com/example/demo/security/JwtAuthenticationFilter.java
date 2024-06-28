package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static String Authorization_Header = "Authorization";
    private static String BEARER_PREFIX = "Bearer ";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromHeader(request.getHeader(Authorization_Header));
        if(token != null && JWTUtil.validateToken(token)){
            String userName = JWTUtil.extractUsername(token);
            Authentication auth = new UsernamePasswordAuthenticationToken(userName,null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);
    }

    private String extractTokenFromHeader(String header) {
        if(header !=null && header.startsWith(BEARER_PREFIX)){
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
