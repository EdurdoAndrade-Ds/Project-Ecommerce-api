package org.ecommerce.ecommerceapi.auth.dto;

public class AuthResponseDTO {
    public String token;

    public AuthResponseDTO(String token) {
        this.token = token;
    }
}
