package com.learn.jwt.security.jwtservice;

import com.learn.jwt.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService; // Injecting the JwtService to validate the token

    @Autowired
    private CustomUserDetailsService userDetailsService; // Injecting the CustomUserDetailsService to load user details

    // The doFilterInternal method is executed for each request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Getting the "Authorization" header from the HTTP request
        String authentication = request.getHeader("Authorization");

        // Checking if the Authorization header is present and starts with "Bearer " (standard JWT header format)
        if (authentication != null && authentication.startsWith("Bearer ")) {
            // Extracting the token from the Authorization header
            String token = authentication.substring(7);

            // Validating the token using the JwtService
            if (jwtService.validateToken(token)) {
                // Extracting the username (subject) from the token
                String username = jwtService.getUsername(token);

                // Loading the user details from the custom UserDetailsService
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Checking if the SecurityContext already has an authentication
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    // If no authentication is set, creating a new UsernamePasswordAuthenticationToken
                    // This will hold the user details and authorities (roles/permissions)
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // Setting the details of the authentication (request-based info)
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Setting the authentication object in the SecurityContextHolder
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }

        // Passing the request and response to the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
