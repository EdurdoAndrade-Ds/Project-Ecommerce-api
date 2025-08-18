package org.ecommerce.ecommerceapi.exceptions;

public class ClientBadRequestException extends RuntimeException {
    public ClientBadRequestException(String message) {
        super(message);
    }
    public ClientBadRequestException() {
        super("Erro na formatacao do cliente");
    }


}
