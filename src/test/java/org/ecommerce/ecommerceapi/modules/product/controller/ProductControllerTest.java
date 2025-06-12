package org.ecommerce.ecommerceapi.modules.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.modules.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void criar() throws Exception {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setNome("Produto Teste");
        requestDTO.setPreco(BigDecimal.valueOf(123.45));
        requestDTO.setDescricao("Descrição do produto");
        requestDTO.setEstoque(10);

        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Produto Teste");
        responseDTO.setPreco(BigDecimal.valueOf(123.45));
        responseDTO.setDescricao("Descrição do produto");
        responseDTO.setEstoque(10);

        when(productService.criar(any(ProductRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Produto Teste"))
                .andExpect(jsonPath("$.preco").value(123.45))
                .andExpect(jsonPath("$.descricao").value("Descrição do produto"))
                .andExpect(jsonPath("$.estoque").value(10));

        verify(productService, times(1)).criar(any(ProductRequestDTO.class));
    }

    @Test
    void listar() throws Exception {
        ProductResponseDTO p1 = new ProductResponseDTO();
        p1.setId(1L);
        p1.setNome("Produto 1");
        p1.setPreco(BigDecimal.valueOf(100.0));
        p1.setDescricao("Descrição 1");
        p1.setEstoque(5);

        ProductResponseDTO p2 = new ProductResponseDTO();
        p2.setId(2L);
        p2.setNome("Produto 2");
        p2.setPreco(BigDecimal.valueOf(200.0));
        p2.setDescricao("Descrição 2");
        p2.setEstoque(15);

        when(productService.listar()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/produtos")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Produto 1"))
                .andExpect(jsonPath("$[0].preco").value(100.0))
                .andExpect(jsonPath("$[0].descricao").value("Descrição 1"))
                .andExpect(jsonPath("$[0].estoque").value(5))
                .andExpect(jsonPath("$[1].nome").value("Produto 2"))
                .andExpect(jsonPath("$[1].preco").value(200.0))
                .andExpect(jsonPath("$[1].descricao").value("Descrição 2"))
                .andExpect(jsonPath("$[1].estoque").value(15));

        verify(productService, times(1)).listar();
    }

    @Test
    void excluir() throws Exception {
        Long idToDelete = 1L;

        doNothing().when(productService).excluir(idToDelete);

        mockMvc.perform(delete("/produtos/{id}", idToDelete))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).excluir(idToDelete);
    }
}
