package org.ecommerce.ecommerceapi.exceptions;

public class ClientConflictException extends RuntimeException {
  public ClientConflictException(String message) {
    super(message);
  }
  public ClientConflictException() {
    super("Ja existe cliente com este username ou e-mail");
  }
}
