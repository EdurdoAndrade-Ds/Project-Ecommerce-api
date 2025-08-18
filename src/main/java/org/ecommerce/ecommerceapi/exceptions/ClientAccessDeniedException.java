package org.ecommerce.ecommerceapi.exceptions;

public class ClientAccessDeniedException extends RuntimeException {
    public ClientAccessDeniedException(String message) {
        super(message);
    }
    public ClientAccessDeniedException() {
        super("Acesso negado!");
    }
}
