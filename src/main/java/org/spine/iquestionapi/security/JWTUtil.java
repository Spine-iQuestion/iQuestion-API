package org.spine.iquestionapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.spine.iquestionapi.config.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * The utility class for the JWT tokens
 */
@Component
public class JWTUtil {

    @Autowired
    private ConfigProperties config;

    /**
     * Creates a JWT token for the given email
     * @param email the email
     * @return the JWT token
     * @throws IllegalArgumentException if the email is null or empty
     * @throws JWTCreationException if the token cannot be created for some reason
     */
    public String generateToken(String email) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer("I.Question")
                .sign(Algorithm.HMAC256(config.getJwtSecret()));
    }

    /**
     * Validates the given JWT token and returns the email
     * @param token the token
     * @return the email
     * @throws JWTVerificationException if the token is invalid
     */
    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(config.getJwtSecret()))
                .withSubject("User Details")
                .withIssuer("I.Question")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }

}