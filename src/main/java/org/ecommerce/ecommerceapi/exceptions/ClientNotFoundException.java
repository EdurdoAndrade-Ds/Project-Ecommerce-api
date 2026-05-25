package org.ecommerce.ecommerceapi.exceptions;

public class ClientNotFoundException extends ResourceNotFoundException {
    public ClientNotFoundException(String message) {
        super(message);
    }
    public ClientNotFoundException() {
        super("Cliente nao encontrado");
    }
}
