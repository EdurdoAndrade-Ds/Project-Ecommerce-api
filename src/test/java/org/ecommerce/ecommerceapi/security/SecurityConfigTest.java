package org.ecommerce.ecommerceapi.security;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;


class SecurityConfigTest {
    @Test
    void testPasswordEncoder() {
        SecurityConfig config = new SecurityConfig();
        assertNotNull(config.passwordEncoder());
    }
}
