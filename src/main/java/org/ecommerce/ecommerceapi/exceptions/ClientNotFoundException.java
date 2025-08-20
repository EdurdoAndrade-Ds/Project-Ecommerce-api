package org.ecommerce.ecommerceapi.exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String message) {
        super(message);
    }
    public ClientNotFoundException() {
        super("Cliente nao encontrado");
    }
}
