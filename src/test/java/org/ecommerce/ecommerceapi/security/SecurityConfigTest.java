package org.ecommerce.ecommerceapi.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@ActiveProfiles("test") // Use um perfil de teste se necessário
public class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        // Configurações iniciais, se necessário
    }

    @Test
    void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String rawPassword = "myPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Verifica se a senha codificada não é igual à senha original
        assertThat(encodedPassword).isNotEqualTo(rawPassword);
        // Verifica se a senha codificada é válida
        assertThat(passwordEncoder.matches(rawPassword, encodedPassword)).isTrue();
    }

    @Test
    void testCorsConfigurationSource() {
        CorsConfigurationSource corsConfigurationSource = securityConfig.corsConfigurationSource();
        MockHttpServletRequest request = new MockHttpServletRequest();
        CorsConfiguration configuration = corsConfigurationSource.getCorsConfiguration(request);

        // Verifica se as origens permitidas estão corretas
        assertThat(configuration.getAllowedOrigins()).containsExactly("*");
        // Verifica se os métodos permitidos estão corretos
        assertThat(configuration.getAllowedMethods()).containsExactly("GET", "POST", "PUT", "DELETE", "OPTIONS");
        // Verifica se os cabeçalhos permitidos estão corretos
        assertThat(configuration.getAllowedHeaders()).containsExactly("Authorization", "Content-Type", "X-Requested-With");
        // Verifica se os cabeçalhos expostos estão corretos
        assertThat(configuration.getExposedHeaders()).containsExactly("Authorization");
        // Verifica se AllowCredentials está definido como false
        assertThat(configuration.getAllowCredentials()).isFalse();
    }

    @Test
    void testSecurityFilterChain() throws Exception {
        HttpSecurity http = Mockito.mock(HttpSecurity.class);
        assertThatCode(() -> securityConfig.securityFilterChain(http)).doesNotThrowAnyException();
    }

    @Test
    void testRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        // use request no teste
    }
}
