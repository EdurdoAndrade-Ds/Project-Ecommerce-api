package org.ecommerce.ecommerceapi.modules.product.service;

import org.ecommerce.ecommerceapi.modules.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductStockUpdateRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductUpdateDTO;
import org.ecommerce.ecommerceapi.modules.product.entities.Product;
import org.ecommerce.ecommerceapi.modules.product.enums.OperacaoEstoque;
import org.ecommerce.ecommerceapi.modules.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setNome("Produto Teste");
        product.setDescricao("Descrição do Produto Teste");
        product.setPreco(new BigDecimal("25.00"));
        product.setEstoque(10);
    }

    @Test
    void testBuscarPorId() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productService.buscarPorId(1L);
        assertEquals(product.getNome(), foundProduct.getNome());
    }

    @Test
    void testBuscarPorIdDTO() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDTO responseDTO = productService.buscarPorIdDTO(1L);
        assertNotNull(responseDTO);
        assertEquals(product.getNome(), responseDTO.getNome());
    }

    @Test
    void testCriar() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setNome("Produto Novo");
        dto.setDescricao("Descrição do Produto Novo");
        dto.setPreco(new BigDecimal("30.00"));
        dto.setEstoque(5);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO response = productService.criar(dto);
        assertEquals(product.getNome(), response.getNome());
    }

    @Test
    void testListar() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        List<ProductResponseDTO> products = productService.listar();
        assertEquals(1, products.size());
        assertEquals(product.getNome(), products.get(0).getNome());
    }

    @Test
    void testExcluir() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.excluir(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAtualizar() {
        ProductUpdateDTO dto = new ProductUpdateDTO();
        dto.setNome("Produto Atualizado");
        dto.setDescricao("Descrição Atualizada");
        dto.setPreco(new BigDecimal("35.00"));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO updatedProduct = productService.atualizar(1L, dto);
        assertEquals(dto.getNome(), updatedProduct.getNome());
    }

    @Test
    void testAtualizarEstoqueAumentar() {
        ProductStockUpdateRequestDTO dto = new ProductStockUpdateRequestDTO();
        dto.setOperacao(OperacaoEstoque.AUMENTAR);
        dto.setQuantidade(5);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.atualizarEstoque(1L, dto);
        assertEquals(15, product.getEstoque());
    }

    @Test
    void testAtualizarEstoqueReduzir() {
        ProductStockUpdateRequestDTO dto = new ProductStockUpdateRequestDTO();
        dto.setOperacao(OperacaoEstoque.REDUZIR);
        dto.setQuantidade(5);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.atualizarEstoque(1L, dto);
        assertEquals(5, product.getEstoque());
    }

    @Test
    void testAtualizarEstoqueReduzirInsuficiente() {
        ProductStockUpdateRequestDTO dto = new ProductStockUpdateRequestDTO();
        dto.setOperacao(OperacaoEstoque.REDUZIR);
        dto.setQuantidade(15);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.atualizarEstoque(1L, dto);
        });
        assertEquals("Estoque insuficiente para redução.", exception.getMessage());
    }
}

