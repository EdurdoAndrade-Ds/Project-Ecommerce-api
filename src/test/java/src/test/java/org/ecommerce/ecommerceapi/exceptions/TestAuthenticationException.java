package org.ecommerce.ecommerceapi.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAuthenticationException {

    @Test
    public void deveCriarExcecaoComMensagem() {
        String mensagem = "Credenciais inválidas";
        AuthenticationException ex = new AuthenticationException(mensagem);

        assertEquals(mensagem, ex.getMessage());
    }

    @Test
    public void deveSerRuntimeException() {
        AuthenticationException ex = new AuthenticationException("Erro");
        assertTrue(ex instanceof RuntimeException);
    }

}
