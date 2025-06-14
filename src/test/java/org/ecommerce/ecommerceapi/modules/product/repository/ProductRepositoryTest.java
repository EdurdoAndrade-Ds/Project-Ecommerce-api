package org.ecommerce.ecommerceapi.modules.product.repository;

import org.ecommerce.ecommerceapi.modules.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // Limpa o repositório antes de cada teste
        productRepository.deleteAll();
    }

    @Test
    @Rollback(false) // Para que os dados sejam persistidos no banco de dados em memória
    void testSaveAndFindProduct() {
        // Cria um novo produto
        Product product = new Product();
        product.setNome("Produto Teste");
        product.setDescricao("Descrição do Produto Teste");
        product.setPreco(BigDecimal.valueOf(99.99));
        product.setEstoque(10);
        product.setDescontoPercentual(15.0);
        product.setDescountedPrice(BigDecimal.valueOf(84.99));

        // Salva o produto
        Product savedProduct = productRepository.save(product);

        // Verifica se o produto foi salvo corretamente
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());
        assertTrue(foundProduct.isPresent());
        assertEquals("Produto Teste", foundProduct.get().getNome());
        assertEquals("Descrição do Produto Teste", foundProduct.get().getDescricao());
        assertEquals(BigDecimal.valueOf(99.99), foundProduct.get().getPreco());
    }

    @Test
    void testDeleteProduct() {
        // Cria e salva um novo produto
        Product product = new Product();
        product.setNome("Produto Teste");
        product.setDescricao("Descrição do Produto Teste");
        product.setPreco(BigDecimal.valueOf(99.99));
        product.setEstoque(10);
        product.setDescontoPercentual(15.0);
        product.setDescountedPrice(BigDecimal.valueOf(84.99));
        Product savedProduct = productRepository.save(product);

        // Deleta o produto
        productRepository.delete(savedProduct);

        // Verifica se o produto foi deletado
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());
        assertFalse(foundProduct.isPresent());
    }
}
