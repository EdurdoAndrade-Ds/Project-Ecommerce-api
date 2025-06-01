package org.ecommerce.ecommerceapi.client.dto;

public class ClientResponseDto {

    private Long id;
    private String name;
    private String email;

    // outros campos que queira retornar na resposta

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
}
