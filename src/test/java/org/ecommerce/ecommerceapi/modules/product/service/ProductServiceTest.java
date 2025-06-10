package org.ecommerce.ecommerceapi.modules.product.service;

import org.ecommerce.ecommerceapi.modules.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.modules.product.entities.Product;
import org.ecommerce.ecommerceapi.modules.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository repository;
    private ProductService service;

    @BeforeEach
    void setup() {
        repository = mock(ProductRepository.class);
        service = new ProductService();
        service.setRepository(repository); // usa o setter de @Data (Lombok)
    }

    @Test
    void deveCriarProdutoComSucesso() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setNome("Produto Teste");
        dto.setDescricao("Descrição");
        dto.setPreco(BigDecimal.valueOf(10.5));
        dto.setEstoque(5);

        Product mockSaved = new Product(1L, dto.getNome(), dto.getDescricao(), dto.getPreco(), dto.getEstoque(), null);

        when(repository.save(any(Product.class))).thenReturn(mockSaved);

        ProductResponseDTO response = service.criar(dto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Produto Teste", response.getNome());
    }

    @Test
    void deveRetornarProdutoPorId() {
        Product product = new Product(1L, "Produto X", "Descrição", BigDecimal.valueOf(100), 10, null);
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDTO dto = service.buscarPorIdDTO(1L);

        assertEquals("Produto X", dto.getNome());
        assertEquals(BigDecimal.valueOf(100), dto.getPreco());
    }

    @Test
    void deveListarProdutos() {
        List<Product> produtos = Arrays.asList(
                new Product(1L, "P1", "D1", BigDecimal.TEN, 2, null),
                new Product(2L, "P2", "D2", BigDecimal.ONE, 4, null)
        );
        when(repository.findAll()).thenReturn(produtos);

        List<ProductResponseDTO> lista = service.listar();

        assertEquals(2, lista.size());
        assertEquals("P1", lista.get(0).getNome());
    }

    @Test
    void deveExcluirProduto() {
        when(repository.existsById(1L)).thenReturn(true);
        service.excluir(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarErroAoExcluirProdutoInexistente() {
        when(repository.existsById(99L)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.excluir(99L));
        assertEquals("Produto não encontrado", exception.getMessage());
    }

    @Test
    void deveLancarErroAoCriarProdutoInvalido() {
        ProductRequestDTO dto = new ProductRequestDTO(); // campos nulos
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.criar(dto));
        assertTrue(exception.getMessage().contains("Nome do produto é obrigatório"));
    }
}
