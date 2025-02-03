package com.learn.jwt.security.service;

import com.learn.jwt.security.dto.*;
import com.learn.jwt.security.exception.InvalidRefreshTokenException;
import com.learn.jwt.security.jwtservice.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        // Create an authentication token with the username and password from the request
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        // Authenticate the token using AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // Set the authentication in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Generate JWT token after successful authentication
        String jwtToken = jwtService.generateToken(authentication.getName(), true);

        String refreshToken = jwtService.generateToken(authentication.getName(), false);

        String username = jwtService.getUsername(jwtToken);

        UserResponseDto userResponse = userService.getByEmail(username);


        // Return the JWT token in the response
        return new LoginResponse(jwtToken, refreshToken, userResponse);
    }


    public LoginResponse getRefreshToken(RefreshTokenDto refreshTokenDto) {
        if (jwtService.validateToken(refreshTokenDto.getRefreshToken())) {
            String username = jwtService.getUsername(refreshTokenDto.getRefreshToken());

            UserResponseDto userResponse = userService.getByEmail(username);

            String accessToken = jwtService.generateToken(username, true);
            String refreshToken = jwtService.generateToken(username, false);

            return new LoginResponse(accessToken, refreshToken, userResponse);
        } else {
            throw new InvalidRefreshTokenException("Invalid refresh token !! ");
        }
    }
}
