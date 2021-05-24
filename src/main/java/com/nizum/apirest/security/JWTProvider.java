package com.nizum.apirest.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@Log4j2
public class JWTProvider {

    @Value(value = "${jwt.expiration.time}")
    public long EXPIRATION_TIME;

    @Value(value = "${jwt.secret.key}")
    public String TOKEN_SECRET;

    @Value(value = "${security.token.bearer.prefix}")
    public String TOKEN_BEARER_PREFIX;

    @Value(value = "${security.header.authorization.key}")
    public String HEADER_AUTHORIZATION_KEY;


    public String getToken(String email) {

        String token = JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(TOKEN_SECRET));

        return TOKEN_BEARER_PREFIX + " " + token;
    }

    public String getEmailFromToken(String token) {
        return JWT.decode(token).getSubject();
    }

    public boolean validateToken(String token) {
        Algorithm algorithm;
        JWTVerifier verifier;

        boolean TokenIsValid = true;

        try {
            algorithm = Algorithm.HMAC512(TOKEN_SECRET);
            verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return TokenIsValid;
        } catch (JWTVerificationException e) {
            log.error("El token expirado");
            e.printStackTrace();
        }

        return !TokenIsValid;
    }
}
