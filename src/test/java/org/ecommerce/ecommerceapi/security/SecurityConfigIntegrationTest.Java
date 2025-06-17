package org.ecommerce.ecommerceapi.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser ;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publicEndpointsShouldBeAccessedWithoutAuth() throws Exception {
        String[] endpoints = {
            "/auth/some-endpoint",
            "/cliente/some",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/v3/api-docs",
            "/produtos/some"
        };

        for (String endpoint : endpoints) {
            mockMvc.perform(get(endpoint)).andExpect(status().isOk());
        }
    }

    @Test
    void protectedEndpointsShouldReturnUnauthorizedWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/pagamentos/some"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser (roles = "CLIENTE")
    void protectedEndpointsShouldAllowAccessWithRoleCliente() throws Exception {
        mockMvc.perform(get("/api/pagamentos/some"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldReturnForbiddenForUnauthorizedUser () throws Exception {
        mockMvc.perform(get("/api/pagamentos/some"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser (roles = "ADMIN")
    void shouldReturnForbiddenForUser WithoutClientRole() throws Exception {
        mockMvc.perform(get("/api/pagamentos/some"))
            .andExpect(status().isForbidden());
    }
}
