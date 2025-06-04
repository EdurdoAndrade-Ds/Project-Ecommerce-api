package org.ecommerce.ecommerceapi.client.dto;

public class ClientResponseDTO{

    private Long id;
    private String name;
    private String email;
    private String telefone; // Adicionado campo telefone

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
