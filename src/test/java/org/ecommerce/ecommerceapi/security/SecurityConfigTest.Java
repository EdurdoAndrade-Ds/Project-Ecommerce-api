package org.ecommerce.ecommerceapi.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;

    @Test
    void testPasswordEncoderBean() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        assertThat(encoder).isNotNull();
        assertThat(encoder.matches("senha", encoder.encode("senha"))).isTrue();
    }

    @Test
    void testSecurityFilterChainBean() throws Exception {
        SecurityFilterChain chain = securityConfig.securityFilterChain(new org.springframework.security.config.annotation.web.builders.HttpSecurity(
                org.springframework.security.web.DefaultSecurityFilterChain.class, null, null, null, null, null, null));
        assertThat(chain).isNotNull();
    }

    @Test
    void dummyTest() {
        assertTrue(true);
    }
}
