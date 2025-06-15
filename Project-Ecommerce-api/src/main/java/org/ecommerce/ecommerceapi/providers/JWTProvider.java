package org.ecommerce.ecommerceapi.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class JWTProvider {

    @Value("${security.token.secret}")
    private String secretKey;

    public String generateToken(String subject, List<String> roles) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer("ecommerce")
                    .withSubject(subject)
                    .withClaim("roles", roles)
                    .withExpiresAt(Instant.now().plus(Duration.ofHours(24)))
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("ecommerce")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token JWT inválido ou expirado");
        }
    }

    public DecodedJWT getDecodedJWT(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("ecommerce")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token JWT inválido ou expirado");
        }
    }
}

