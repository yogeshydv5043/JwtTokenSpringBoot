package com.learn.jwt.security.jwtservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.token.expiration}")
    private Long EXPIRATION_TIME; // The token expiration time will be loaded from the property file

    @Value("${jwt.token.secretKey}")
    private String SECRET_KEY; // The secret key will be loaded from the property file

    @Value("${jwt.token.refresh.expiration}")
    public  Long REFRESH_EXPIRATION_TIME;

    private final Logger logger = LoggerFactory.getLogger(JwtService.class);

    // Method to generate the token
    public String generateToken(String username,boolean isAccessToken) {
        Long expTime =isAccessToken ? EXPIRATION_TIME : REFRESH_EXPIRATION_TIME;
        return Jwts.builder()
                .setSubject(username) // Set the username as the subject of the token
                .setIssuedAt(new Date()) // Set the current date as the issued date of the token
                .setExpiration(new Date(System.currentTimeMillis() + expTime)) // Set the expiration time for the token
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256) // Sign the token with the secret key and HS256 algorithm
                .compact(); // Return the token as a compact string
    }

    // Method to get the username (subject) from the token
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())) // Set the signing key
                .build()
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Get the body (claims) of the token
        return claims.getSubject(); // Extract the username (subject) from the body
    }

    // Method to check if the token is expired
    public boolean isTokenExpired(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())) // Set the signing key
                .build()
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Get the body (claims) of the token
        return claims.getExpiration().before(new Date()); // If the expiration date is before the current date, return true
    }

    // Method to validate the token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())) // Set the signing key
                    .build()
                    .parseClaimsJws(token); // Parse the token
            return true; // If the token is parsed successfully, return true (valid token)
        } catch (JwtException e) {
            // If an exception is thrown, the token is invalid
            logger.error("Invalid JWT token: {}", token);
            logger.error("Jwt Exception: ", e); // Log the stack trace for debugging
            return false;
        }
    }
}