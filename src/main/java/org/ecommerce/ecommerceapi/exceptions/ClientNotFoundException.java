package org.ecommerce.ecommerceapi.exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String message) {
        super(message);
    }
    public ClientNotFoundException() {
        super("Client nao existe - tratamento da execao global");
    }
}
