package org.ecommerce.ecommerceapi.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationExceptionTest {

    @Test
    void deveRetornarMensagemPadrao() {
        AuthenticationException exception = new AuthenticationException();
        assertEquals("Usuario/senha incorreto", exception.getMessage());
    }

    @Test
    void deveRetornarMensagemCustomizada() {
        AuthenticationException exception = new AuthenticationException("Acesso negado");
        assertEquals("Acesso negado", exception.getMessage());
    }
}