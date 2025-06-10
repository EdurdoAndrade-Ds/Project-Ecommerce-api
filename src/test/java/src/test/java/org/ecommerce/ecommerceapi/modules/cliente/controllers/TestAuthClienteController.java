package src.test.java.org.ecommerce.ecommerceapi.modules.cliente.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.ecommerce.ecommerceapi.modules.cliente.controllers.AuthClienteController;
import org.ecommerce.ecommerceapi.modules.cliente.dto.AuthClienteDTO;
import org.ecommerce.ecommerceapi.modules.cliente.dto.AuthClienteResponseDTO;
import org.ecommerce.ecommerceapi.modules.cliente.useCases.AuthClienteUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthClienteController.class)
class AuthClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthClienteUseCase authClienteUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve autenticar cliente com sucesso e retornar token")
    void autenticarComSucesso() throws Exception {
        AuthClienteDTO dto = new AuthClienteDTO("joaosilva", "senha123456");

        AuthClienteResponseDTO response = new AuthClienteResponseDTO();
        response.setToken("jwt-token-exemplo");
        response.setId(1L);
        response.setUsername("joaosilva");

        Mockito.when(authClienteUseCase.execute(any(AuthClienteDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/auth/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token-exemplo"))
                .andExpect(jsonPath("$.username").value("joaosilva"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Deve retornar erro 401 quando credenciais forem inválidas")
    void autenticarComFalha() throws Exception {
        AuthClienteDTO dto = new AuthClienteDTO("joaosilva", "senhaErrada");

        Mockito.when(authClienteUseCase.execute(any(AuthClienteDTO.class)))
                .thenThrow(new RuntimeException("Usuário ou senha incorretos"));

        mockMvc.perform(post("/auth/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Usuário ou senha incorretos"));
    }
}
