package com.example.employeepayrollapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtToken {

    @Autowired
    static String TOKEN_SECRET = "Lock";
    @Autowired
    static Map<Long, String> activeTokens = new HashMap<>();

    // Create JWT token
    public String createToken(Long userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            String token = JWT.create()
                    .withClaim("user_id", userId)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour expiration
                    .sign(algorithm);
            activeTokens.put(userId, token);
            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Error while creating token");
        }
    }

    // Decode JWT token and extract user ID
    public Long decodeToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("user_id").asLong();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid or expired token.");
        }
    }

    // Check if user is logged in based on token
    public boolean isUserLoggedIn(Long userId, String token) {
        return activeTokens.containsKey(userId) && activeTokens.get(userId).equals(token);
    }

    // Log out user by removing token
    public void logoutUser(Long userId) {
        activeTokens.remove(userId);
    }

    // Get the current user ID from the token
    public Long getCurrentUserId(String token) {
        return decodeToken(token); // Extract user ID from token
    }

    // Get the token for a logged-in user
    public String getCurrentToken(Long userId) {
        return activeTokens.get(userId); // Get active token for user
    }
}
