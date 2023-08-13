package com.vedha.blog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwtTokenInHttpRequestHeader = getJwtTokenInHttpRequestHeader(request);

        if (StringUtils.hasText(jwtTokenInHttpRequestHeader) && jwtTokenProvider.validateJwtToken(jwtTokenInHttpRequestHeader)) {

            String userNameInJwtToken = jwtTokenProvider.getUserNameInJwtToken(jwtTokenInHttpRequestHeader);

            UserDetails userDetails = userDetailsService.loadUserByUsername(userNameInJwtToken);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            filterChain.doFilter(request, response);

        }else {

            filterChain.doFilter(request, response);
        }

    }

    // Get Jwt Bearer Token in Http Request Header
    private String getJwtTokenInHttpRequestHeader(HttpServletRequest request) {

        String authorizationToken = request.getHeader("Authorization");

        if (StringUtils.hasText(authorizationToken) && authorizationToken.startsWith("Bearer ")) {

            return authorizationToken.subSequence(7, authorizationToken.length()).toString();
        }else {

            return null;
        }

    }
}
