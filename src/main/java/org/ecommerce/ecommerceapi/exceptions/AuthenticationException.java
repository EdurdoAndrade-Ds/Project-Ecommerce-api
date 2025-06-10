package org.ecommerce.ecommerceapi.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Usuario/senha incorreto");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}

