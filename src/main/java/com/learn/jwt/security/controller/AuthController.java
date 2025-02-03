package com.learn.jwt.security.controller;

import com.learn.jwt.security.dto.LoginRequest;
import com.learn.jwt.security.dto.LoginResponse;
import com.learn.jwt.security.dto.RefreshTokenDto;
import com.learn.jwt.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody RefreshTokenDto refreshTokenDto) {
        LoginResponse loginResponse = authService.getRefreshToken(refreshTokenDto);
        return ResponseEntity.ok(loginResponse);
    }
}
