package org.ecommerce.ecommerceapi.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
public class JWTProvider {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final String secretKey;

    public JWTProvider(@Value("${jwt.secret:mykeyssecret}") String secret) {
        this.secretKey = secret;
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm).build();
    }

    public String generateToken(String subject, List<String> roles) {
        try {
            return JWT.create()
                    .withIssuer("ecommerce_api")
                    .withSubject(subject)
                    .withClaim("roles", roles)
                    .withExpiresAt(Instant.now().plus(Duration.ofHours(1)))
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    public String validateToken(String token) {
        try {
            return JWT.require(algorithm)
                    .withIssuer("ecommerce_api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token JWT inválido ou expirado");
        }
    }

    public List<String> getRolesFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("roles").asList(String.class);
    }

    public DecodedJWT getDecodedJWT(String token) {
        try {
            return JWT.require(algorithm)
                    .withIssuer("ecommerce_api")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token JWT inválido ou expirado");
        }
    }

    public String extractUsername(String token) {
        return getDecodedJWT(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            validateToken(token);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
}


