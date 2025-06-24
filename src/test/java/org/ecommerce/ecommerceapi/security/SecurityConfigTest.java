package org.ecommerce.ecommerceapi.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

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
        CorsConfiguration configuration = corsConfigurationSource.getCorsConfiguration(null);

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
        HttpSecurity http = new HttpSecurity(null, null, null, null, null, null, null);
        securityConfig.securityFilterChain(http);

        // Verifica se a configuração de CORS foi aplicada
        assertThat(http.getCors()).isNotNull();
        // Verifica se a configuração de CSRF foi desativada
        assertThat(http.getCsrf().isEnabled()).isFalse();
        // Verifica se a política de sessão está definida como stateless
        assertThat(http.getSessionManagement().getSessionCreationPolicy()).isEqualTo(SessionCreationPolicy.STATELESS);
    }
}
