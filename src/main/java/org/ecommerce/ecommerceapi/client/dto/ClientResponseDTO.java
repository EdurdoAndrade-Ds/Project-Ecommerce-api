package org.ecommerce.ecommerceapi.client.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ClientResponseDTO {
    @Schema(description = "ID único do cliente", example = "1")
    private Long id;

    @Schema(description = "Nome completo do cliente", example = "Eduardo")
    private String name;

    @Schema(description = "Endereço de e-mail", example = "eduardo@email.com")
    private String email;

    @Schema(description = "Telefone com DDD", example = "11999999999")
    private String telefone;

    // Construtor padrão
    public ClientResponseDTO() {}

    // Construtor com campos
    public ClientResponseDTO(Long id, String name, String email, String telefone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.telefone = telefone;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
}
