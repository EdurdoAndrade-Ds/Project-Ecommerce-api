package org.ecommerce.ecommerceapi.security;

import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.mock.web.MockHttpServletRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import java.util.List;
import org.ecommerce.ecommerceapi.modules.client.useCases.AuthClientUseCase;
import org.ecommerce.ecommerceapi.modules.client.dto.AuthClientDTO;
import org.ecommerce.ecommerceapi.modules.client.dto.AuthClientResponseDTO;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityConfigTest {

    @MockBean
    private org.ecommerce.ecommerceapi.providers.JWTProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private org.ecommerce.ecommerceapi.modules.client.useCases.CreateClientUseCase createClientUseCase;
    @MockBean
    private org.ecommerce.ecommerceapi.modules.client.useCases.DeleteClientUseCase deleteClientUseCase;
    @MockBean
    private org.ecommerce.ecommerceapi.modules.client.useCases.UpdateClientUseCase updateClientUseCase;
    @MockBean
    private AuthClientUseCase authClientUseCase;

    // Remover os mocks de PasswordEncoder e CorsConfigurationSource
    @BeforeEach
    void setup() {
        ClientEntity mockClient = new ClientEntity();
        mockClient.setName("Teste");
        mockClient.setUsername("teste");
        mockClient.setEmail("teste@teste.com");
        mockClient.setPassword("123456");
        when(createClientUseCase.execute(any())).thenReturn(mockClient);
        // Mock do JWTProvider
        when(jwtProvider.generateToken(any(String.class), any(List.class))).thenReturn("fake-jwt-token");
        when(jwtProvider.validateToken(any(String.class))).thenReturn("teste");

        AuthClientResponseDTO mockResponse = new AuthClientResponseDTO();
        mockResponse.setToken("fake-jwt-token");
        mockResponse.setId(1L);
        mockResponse.setUsername("teste");
        when(authClientUseCase.execute(any(AuthClientDTO.class))).thenReturn(mockResponse);
    }

    @Test
    void testPasswordEncoder() {
        String raw = "123456";
        String encoded = passwordEncoder.encode(raw);

        assertThat(passwordEncoder.matches(raw, encoded)).isTrue();
    }




    @Test
    void testCorsConfigurationSource() {
        var request = new MockHttpServletRequest();
        var config = corsConfigurationSource.getCorsConfiguration(request);

        assertThat(config.getAllowedOrigins()).contains("*");
        assertThat(config.getAllowedMethods())
                .contains("GET", "POST", "PUT", "DELETE", "OPTIONS");
        assertThat(config.getAllowedHeaders()).contains("Authorization", "Content-Type", "X-Requested-With");
    }

    @Test
    void testPublicEndpointsAreAccessible() throws Exception {
        // /client é público (não deve dar 401/403)
        mockMvc.perform(post("/client")
                        .contentType("application/json")
                        .content("{\"name\":\"Teste\",\"username\":\"teste\",\"email\":\"teste@teste.com\",\"password\":\"123456\"}"))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    assertThat(status).isNotIn(401, 403);
                });

        // /api/v1/version é público
        mockMvc.perform(get("/api/v1/version"))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    assertThat(status).isNotIn(401, 403);
                });

        // /auth/login é público
        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content("{\"username\":\"teste\",\"password\":\"123456\"}"))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    assertThat(status).isNotIn(401, 403);
                });

        // /auth/client é público
        mockMvc.perform(post("/auth/client")
                        .contentType("application/json")
                        .content("{\"username\":\"teste\",\"password\":\"123456\"}"))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    assertThat(status).isNotIn(401, 403);
                });
    }

    @Test
    void testProtectedEndpointsRequireAuth() throws Exception {
        mockMvc.perform(get("/api/pagamentos/1"))
                .andExpect(status().isForbidden()); // 403 sem token
    }

    @Test
    void testAuthEndpointsArePublic() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content("{\"username\":\"teste\",\"password\":\"123456\"}"))
                .andExpect(result ->
                    assertThat(result.getResponse().getStatus()).isNotIn(401, 403)
                );

        mockMvc.perform(post("/auth/client")
                .contentType("application/json")
                .content("{\"username\":\"teste\",\"password\":\"123456\"}"))
                .andExpect(result ->
                    assertThat(result.getResponse().getStatus()).isNotIn(401, 403)
                );
    }

}
