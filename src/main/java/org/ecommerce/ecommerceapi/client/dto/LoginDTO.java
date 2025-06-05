package org.ecommerce.ecommerceapi.client.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Credenciais para login")
public class LoginDTO {
    @Schema(description = "Email do cliente", example = "cliente@teste.com")
    private String email;

    @Schema(description = "Senha do cliente", example = "123456")
    private String senha;

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

