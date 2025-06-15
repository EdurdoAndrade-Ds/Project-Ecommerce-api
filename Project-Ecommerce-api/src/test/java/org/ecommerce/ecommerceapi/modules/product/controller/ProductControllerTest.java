package org.ecommerce.ecommerceapi.product.controller;

<<<<<<< Updated upstream
import org.ecommerce.ecommerceapi.modules.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductStockUpdateRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductUpdateDTO;
import org.ecommerce.ecommerceapi.modules.product.enums.OperacaoEstoque;
import org.ecommerce.ecommerceapi.modules.product.service.ProductService;
=======
import org.ecommerce.ecommerceapi.product.model.Product;
import org.ecommerce.ecommerceapi.product.service.ProductService;
>>>>>>> Stashed changes
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
<<<<<<< Updated upstream
import java.util.Collections;
=======
import java.util.ArrayList;
>>>>>>> Stashed changes
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
<<<<<<< Updated upstream
    void testCriar() {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setNome("Produto Teste");
        requestDTO.setDescricao("Descrição do Produto Teste");
        requestDTO.setPreco(new BigDecimal("25.00"));
        requestDTO.setEstoque(10);

        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Produto Teste");
        responseDTO.setDescricao("Descrição do Produto Teste");
        responseDTO.setPreco(new BigDecimal("25.00"));
        responseDTO.setEstoque(10);
=======
    void testCreateProduct() {
        Product productToCreate = new Product();
        productToCreate.setName("Produto Teste");
        productToCreate.setPrice(BigDecimal.valueOf(10.0));

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName(productToCreate.getName());
        savedProduct.setPrice(productToCreate.getPrice());
>>>>>>> Stashed changes

        when(productService.salvar(any(Product.class))).thenReturn(savedProduct);

<<<<<<< Updated upstream
        ResponseEntity<ProductResponseDTO> response = productController.criar(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Produto Teste", response.getBody().getNome());
    }

    @Test
    void testBuscarPorIdFound() {
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Produto Teste");

        when(productService.buscarPorIdDTO(1L)).thenReturn(responseDTO);

        ResponseEntity<ProductResponseDTO> response = productController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Produto Teste", response.getBody().getNome());
    }

    @Test
    void testBuscarPorIdNotFound() {
        when(productService.buscarPorIdDTO(anyLong())).thenThrow(new RuntimeException("Produto não encontrado"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productController.buscarPorId(1L);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
=======
        ResponseEntity<Product> response = productController.create(productToCreate);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Produto Teste", response.getBody().getName());
    }

    @Test
    void testListProducts() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Produto 1");
        productList.add(product1);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Produto 2");
        productList.add(product2);

        when(productService.buscarTodos()).thenReturn(productList);

        ResponseEntity<List<Product>> response = productController.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Produto 1", response.getBody().get(0).getName());
    }

    @Test
    void testSearchForIdProdutoFound() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Produto Teste");

        when(productService.buscarPorId(1L)).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.searchForIdProduto(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto Teste", response.getBody().getName());
    }

    @Test
    void testSearchForIdProdutoNotFound() {
        when(productService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.searchForIdProduto(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody()); // Verifica se o corpo da resposta é null
    }

    @Test
    void testUpdateProduto() {
        Product productToUpdate = new Product();
        productToUpdate.setId(1L);
        productToUpdate.setName("Produto Atualizado");
        productToUpdate.setPrice(BigDecimal.valueOf(15.0));

        when(productService.atualizar(any(Product.class))).thenReturn(productToUpdate);

        ResponseEntity<Product> response = productController.updateProduto(1L, productToUpdate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto Atualizado", response.getBody().getName());
    }

    @Test
    void testDeleteProduto() {
        doNothing().when(productService).deletar(1L);

        ResponseEntity<Void> response = productController.deleteProduto(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deletar(1L);
>>>>>>> Stashed changes
    }

    @Test
    void testListar() {
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Produto Teste");

        when(productService.listar()).thenReturn(Collections.singletonList(responseDTO));

        ResponseEntity<List<ProductResponseDTO>> response = productController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Produto Teste", response.getBody().get(0).getNome());
    }

    @Test
    void testExcluir() {
        doNothing().when(productService).excluir(1L);

        ResponseEntity<Void> response = productController.excluir(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).excluir(1L);
    }

    @Test
    void testAtualizar() {
        ProductUpdateDTO updateDTO = new ProductUpdateDTO();
        updateDTO.setNome("Produto Atualizado");
        updateDTO.setDescricao("Descrição Atualizada");
        updateDTO.setPreco(new BigDecimal("30.00"));

        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Produto Atualizado");

        when(productService.atualizar(anyLong(), any(ProductUpdateDTO.class))).thenReturn(responseDTO);

        ResponseEntity<ProductResponseDTO> response = productController.atualizar(1L, updateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Produto Atualizado", response.getBody().getNome());
    }

    @Test
    void testAtualizarEstoque() {
        ProductStockUpdateRequestDTO stockUpdateDTO = new ProductStockUpdateRequestDTO();
        stockUpdateDTO.setOperacao(OperacaoEstoque.AUMENTAR);
        stockUpdateDTO.setQuantidade(5);

        doNothing().when(productService).atualizarEstoque(anyLong(), any(ProductStockUpdateRequestDTO.class));

        ResponseEntity<Void> response = productController.atualizarEstoque(1L, stockUpdateDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).atualizarEstoque(1L, stockUpdateDTO);
    }
}