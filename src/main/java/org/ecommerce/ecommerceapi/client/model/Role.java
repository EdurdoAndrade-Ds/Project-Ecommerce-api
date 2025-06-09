package org.ecommerce.ecommerceapi.client.model;

public enum Role {
    CLIENTE("CLIENTE"),
    ADMIN("ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
