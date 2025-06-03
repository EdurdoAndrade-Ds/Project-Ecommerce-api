package org.ecommerce.ecommerceapi.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTProvider {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JWTProvider(@Value("${jwt.secret:mykeyssecret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm).build();
    }

    public String validateToken(String token) {
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }

    public DecodedJWT getDecodedJWT(String token) {
        return verifier.verify(token);
    }
}