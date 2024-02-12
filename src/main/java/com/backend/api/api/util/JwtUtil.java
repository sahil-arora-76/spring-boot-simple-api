package com.backend.api.api.util;

import com.backend.api.api.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;


@Component
public class JwtUtil {

    @Value("${jwt_secret}")
    private String secret;
    public JwtUtil() {
        System.out.println(secret);
    }
    public String generateToken(User user) throws IllegalArgumentException, JWTCreationException {
        Date date = new Date();
        long time = date.getTime();
        Date expirationTime = new Date(time + (24 * 60 * 60 * 1000));
        return JWT.create()
                .withSubject("User Details")
                .withClaim("id", user.getId())
                .withExpiresAt(expirationTime)
                .withClaim("password", user.getPassword())
                .withClaim("username", user.getUsername())
                .withIssuedAt(new Date())
                .withIssuer("jwttoken")
                .sign(Algorithm.HMAC256(secret));
    }

    public User validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("jwttoken")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        User user = new User();
        user.setId(jwt.getClaim("id").asString());
        user.setPassword(jwt.getClaim("password").asString());
        user.setUsername(jwt.getClaim("username").asString());
        return user;
    }
}


