package org.ecommerce.ecommerceapi.modules.product.controller;

import org.ecommerce.ecommerceapi.modules.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductStockUpdateRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductUpdateDTO;
import org.ecommerce.ecommerceapi.modules.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    void criarDeveRetornar201QuandoProdutoCriadoComSucesso() {
        // Arrange
        ProductRequestDTO productRequest = new ProductRequestDTO();
        ProductResponseDTO productResponse = new ProductResponseDTO();
        when(productService.criar(any(ProductRequestDTO.class))).thenReturn(productResponse);

        // Act
        ResponseEntity<ProductResponseDTO> response = productController.criar(productRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productResponse, response.getBody());
        verify(productService, times(1)).criar(any(ProductRequestDTO.class));
    }

    @Test
    void buscarPorIdDeveRetornar200QuandoProdutoEncontrado() {
        // Arrange
        Long productId = 1L;
        ProductResponseDTO productResponse = new ProductResponseDTO();
        when(productService.buscarPorIdDTO(productId)).thenReturn(productResponse);

        // Act
        ResponseEntity<ProductResponseDTO> response = productController.buscarPorId(productId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productResponse, response.getBody());
        verify(productService, times(1)).buscarPorIdDTO(productId);
    }

    @Test
    void listarDeveRetornar200QuandoProdutosEncontrados() {
        // Arrange
        ProductResponseDTO productResponse = new ProductResponseDTO();
        when(productService.listar()).thenReturn(Collections.singletonList(productResponse));

        // Act
        ResponseEntity<List<ProductResponseDTO>> response = productController.listar();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(productResponse, response.getBody().get(0));
        verify(productService, times(1)).listar();
    }

    @Test
    void excluirDeveRetornar204QuandoProdutoExcluidoComSucesso() {
        // Arrange
        Long productId = 1L;

        // Act
        ResponseEntity<Void> response = productController.excluir(productId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).excluir(productId);
    }

    @Test
    void atualizarDeveRetornar200QuandoProdutoAtualizadoComSucesso() {
        // Arrange
        Long productId = 1L;
        ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
        ProductResponseDTO productResponse = new ProductResponseDTO();
        when(productService.atualizar(eq(productId), any(ProductUpdateDTO.class))).thenReturn(productResponse);

        // Act
        ResponseEntity<ProductResponseDTO> response = productController.atualizar(productId, productUpdateDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productResponse, response.getBody());
        verify(productService, times(1)).atualizar(eq(productId), any(ProductUpdateDTO.class));
    }

    @Test
    void atualizarEstoqueDeveRetornar204QuandoEstoqueAtualizadoComSucesso() {
        // Arrange
        Long productId = 1L;
        ProductStockUpdateRequestDTO stockUpdateRequest = new ProductStockUpdateRequestDTO();

        // Act
        ResponseEntity<Void> response = productController.atualizarEstoque(productId, stockUpdateRequest);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).atualizarEstoque(eq(productId), any(ProductStockUpdateRequestDTO.class));
    }

    @Test
    void aplicarDescontoDeveRetornar200QuandoDescontoAplicadoComSucesso() {
        // Arrange
        Long productId = 1L;
        Double discountPercentage = 10.0;
        ProductResponseDTO productResponse = new ProductResponseDTO();
        when(productService.aplicarDesconto(productId, discountPercentage)).thenReturn(productResponse);

        // Act
        ResponseEntity<ProductResponseDTO> response = productController.aplicarDesconto(productId, discountPercentage);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productResponse, response.getBody());
        verify(productService, times(1)).aplicarDesconto(productId, discountPercentage);
    }
}