package org.ecommerce.ecommerceapi.exceptions;

public class ClientUnauthorizedException extends RuntimeException {
    public ClientUnauthorizedException(String message) {
        super(message);
    }

    public ClientUnauthorizedException() {
        super("Cliente nao autorizado!");
    }
}
