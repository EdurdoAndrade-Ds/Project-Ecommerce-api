package org.ecommerce.ecommerceapi.providers;

import com.auth0.jwt.interfaces.DecodedJWT;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JWTProviderTest {

    private JWTProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProvider = new JWTProvider();

        // injetar manualmente o valor do secretKey já que não usamos o Spring aqui
        try {
            java.lang.reflect.Field secretField = JWTProvider.class.getDeclaredField("secretKey");
            secretField.setAccessible(true);
            secretField.set(jwtProvider, "test-secret-key");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao injetar chave secreta", e);
        }
    }

    @Test
    void testGenerateAndValidateToken() {
        String token = jwtProvider.generateToken("usuario123", List.of("CLIENTE", "ADMIN"));
        assertNotNull(token);

        String subject = jwtProvider.validateToken(token);
        assertEquals("usuario123", subject);
    }

    @Test
    void testGetDecodedJWT() {
        String token = jwtProvider.generateToken("usuario456", List.of("CLIENTE"));
        DecodedJWT decodedJWT = jwtProvider.getDecodedJWT(token);

        assertNotNull(decodedJWT);
        assertEquals("usuario456", decodedJWT.getSubject());
        assertEquals("ecommerce", decodedJWT.getIssuer());
        assertEquals(List.of("CLIENTE"), decodedJWT.getClaim("roles").asList(String.class));
    }

    @Test
    void testValidateTokenInvalido() {
        String tokenInvalido = "abc.def.ghi"; // token malformado

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            jwtProvider.validateToken(tokenInvalido);
        });

        assertEquals("Token JWT inválido ou expirado", exception.getMessage());
    }
}
