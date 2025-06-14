package org.ecommerce.ecommerceapi.modules.product.service;

import org.ecommerce.ecommerceapi.modules.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductStockUpdateRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductUpdateDTO;
import org.ecommerce.ecommerceapi.modules.product.entity.Product;
import org.ecommerce.ecommerceapi.modules.product.enums.OperacaoEstoque;
import org.ecommerce.ecommerceapi.modules.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarProduto() {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setNome("Produto Teste");
        requestDTO.setDescricao("Descrição do Produto Teste");
        requestDTO.setPreco(BigDecimal.valueOf(99.99));
        requestDTO.setEstoque(10);

        Product product = new Product();
        product.setId(1L);
        product.setNome(requestDTO.getNome());
        product.setDescricao(requestDTO.getDescricao());
        product.setPreco(requestDTO.getPreco());
        product.setEstoque(requestDTO.getEstoque());

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO responseDTO = productService.criar(requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Produto Teste", responseDTO.getNome());
        assertEquals("Descrição do Produto Teste", responseDTO.getDescricao());
        assertEquals(BigDecimal.valueOf(99.99), responseDTO.getPreco());
        assertEquals(10, responseDTO.getEstoque());
    }

    @Test
    void testBuscarPorId() {
        Product product = new Product();
        product.setId(1L);
        product.setNome("Produto Teste");
        product.setDescricao("Descrição do Produto Teste");
        product.setPreco(BigDecimal.valueOf(99.99));
        product.setEstoque(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productService.buscarPorId(1L);
        assertNotNull(foundProduct);
        assertEquals("Produto Teste", foundProduct.getNome());
    }

    @Test
    void testBuscarPorIdProdutoNaoEncontrado() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.buscarPorId(1L);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
    }

    @Test
    void testAtualizarProduto() {
        ProductUpdateDTO updateDTO = new ProductUpdateDTO();
        updateDTO.setNome("Produto Atualizado");
        updateDTO.setDescricao("Descrição Atualizada");
        updateDTO.setPreco(BigDecimal.valueOf(89.99));

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setNome("Produto Teste");
        existingProduct.setDescricao("Descrição do Produto Teste");
        existingProduct.setPreco(BigDecimal.valueOf(99.99));
        existingProduct.setEstoque(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        ProductResponseDTO updatedProduct = productService.atualizar(1L, updateDTO);

        assertNotNull(updatedProduct);
        assertEquals("Produto Atualizado", updatedProduct.getNome());
        assertEquals("Descrição Atualizada", updatedProduct.getDescricao());
        assertEquals(BigDecimal.valueOf(89.99), updatedProduct.getPreco());
    }

    @Test
    void testAtualizarEstoqueAumentar() {
        ProductStockUpdateRequestDTO stockUpdateDTO = new ProductStockUpdateRequestDTO();
        stockUpdateDTO.setOperacao(OperacaoEstoque.AUMENTAR);
        stockUpdateDTO.setQuantidade(5);

        Product product = new Product();
        product.setId(1L);
        product.setNome("Produto Teste");
        product.setDescricao("Descrição do Produto Teste");
        product.setPreco(BigDecimal.valueOf(99.99));
        product.setEstoque(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.atualizarEstoque(1L, stockUpdateDTO);

        assertEquals(15, product.getEstoque());
    }

    @Test
    void testAtualizarEstoqueReduzir() {
        ProductStockUpdateRequestDTO stockUpdateDTO = new ProductStockUpdateRequestDTO();
        stockUpdateDTO.setOperacao(OperacaoEstoque.REDUZIR);
        stockUpdateDTO.setQuantidade(5);

        Product product = new Product();
        product.setId(1L);
        product.setNome("Produto Teste");
        product.setDescricao("Descrição do Produto Teste");
        product.setPreco(BigDecimal.valueOf(99.99));
        product.setEstoque(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.atualizarEstoque(1L, stockUpdateDTO);

        assertEquals(5, product.getEstoque());
    }

    @Test
    void testExcluirProduto() {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.excluir(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAplicarDesconto() {
        Product product = new Product();
        product.setId(1L);
        product.setPreco(BigDecimal.valueOf(100.00));
        product.setDescontoPercentual(10.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO responseDTO = productService.aplicarDesconto(1L, 10.0);

        assertNotNull(responseDTO);
        // Use BigDecimal para comparação
        assertEquals(0, BigDecimal.valueOf(90.00).compareTo(responseDTO.getDescountedPrice()));
    }

}
