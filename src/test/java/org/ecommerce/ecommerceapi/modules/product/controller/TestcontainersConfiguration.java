package org.ecommerce.ecommerceapi.modules.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.ecommerce.ecommerceapi.modules.product.controller.ProductController;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.modules.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // para converter objetos em JSON

    @MockBean
    private ProductService productService; // mock do service (não acessa o banco real)

    @Test
    @DisplayName("Deve criar um novo produto com sucesso")
    void criarProdutoComSucesso() throws Exception {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setNome("Produto Teste");
        requestDTO.setDescricao("Descrição Teste");
        requestDTO.setPreco(BigDecimal.valueOf(99.99));
        requestDTO.setEstoque(10);

        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Produto Teste");
        responseDTO.setDescricao("Descrição Teste");
        responseDTO.setPreco(BigDecimal.valueOf(99.99));
        responseDTO.setEstoque(10);

        // Quando o service for chamado, retorna o responseDTO
        Mockito.when(productService.criar(any(ProductRequestDTO.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Produto Teste"))
                .andExpect(jsonPath("$.descricao").value("Descrição Teste"))
                .andExpect(jsonPath("$.preco").value(99.99))
                .andExpect(jsonPath("$.estoque").value(10));
    }

    @Test
    @DisplayName("Deve listar todos os produtos")
    void listarProdutos() throws Exception {
        ProductResponseDTO produto1 = new ProductResponseDTO();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setDescricao("Descricao 1");
        produto1.setPreco(BigDecimal.valueOf(50));
        produto1.setEstoque(5);

        ProductResponseDTO produto2 = new ProductResponseDTO();
        produto2.setId(2L);
        produto2.setNome("Produto 2");
        produto2.setDescricao("Descricao 2");
        produto2.setPreco(BigDecimal.valueOf(150));
        produto2.setEstoque(20);

        Mockito.when(productService.listar()).thenReturn(List.of(produto1, produto2));

        mockMvc.perform(get("/produtos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    @DisplayName("Deve excluir um produto pelo ID")
    void excluirProduto() throws Exception {
        mockMvc.perform(delete("/produtos/{id}", 1L))
                .andExpect(status().isNoContent());

        Mockito.verify(productService).excluir(1L);
    }
}
