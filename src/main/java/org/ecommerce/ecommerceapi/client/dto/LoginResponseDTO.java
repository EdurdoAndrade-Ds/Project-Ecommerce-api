package org.ecommerce.ecommerceapi.client.dto;

public class LoginResponseDTO {
    private String token;
    private String tipo;

    public LoginResponseDTO(String token) {
        this.token = token;
        this.tipo = "Bearer";
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}


