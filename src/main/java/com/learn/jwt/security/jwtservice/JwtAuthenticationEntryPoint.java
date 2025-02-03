package com.learn.jwt.security.jwtservice;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // This method is called when an unauthenticated user attempts to access a secured resource.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // Setting the HTTP response status to 401 (Unauthorized), indicating that authentication is required
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Setting the response content type to JSON to match the expected response format
        response.setContentType("application/json");

        // Writing the error message in JSON format to the response body, indicating that the request is unauthorized
        response.getWriter().write("{\"error\":\"unauthorized\"}");

    }
}
