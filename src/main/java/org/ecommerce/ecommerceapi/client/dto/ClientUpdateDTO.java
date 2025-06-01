package org.ecommerce.ecommerceapi.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class ClientUpdateDTO {

    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String name;

    @Email(message = "Email deve ser válido")
    private String email;

    // outros campos que podem ser atualizados

    // Getters e setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
