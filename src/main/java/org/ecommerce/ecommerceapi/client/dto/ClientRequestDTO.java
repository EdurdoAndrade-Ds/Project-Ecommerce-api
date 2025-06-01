package org.ecommerce.ecommerceapi.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequestDTO {
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Deve ser um endereço de e-mail válido")
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\d{10,15}", message = "O telefone deve conter apenas números (10 a 15 dígitos)")
    private String telefone;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres")
    private String senha;

    public ClientRequestDTO() {}

    public ClientRequestDTO(String name, String email, String telefone, String senha) {
        this.name = name;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    // getters e setters
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
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}

