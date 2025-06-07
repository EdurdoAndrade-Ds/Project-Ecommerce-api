package org.ecommerce.ecommerceapi.security;

public enum Role {
    CLIENTE("ROLE_CLIENTE");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
} 