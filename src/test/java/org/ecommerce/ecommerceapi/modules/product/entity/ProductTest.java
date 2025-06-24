package org.ecommerce.ecommerceapi.modules.product.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setNome("Produto Teste");
        product.setDescricao("Descrição do Produto Teste");
        product.setPreco(BigDecimal.valueOf(100.00));
        product.setEstoque(10);
    }

    @Test
    void testCalcularDiscountedPriceSemDesconto() {
        // Arrange
        product.setDescontoPercentual(0.0);

        // Act
        BigDecimal discountedPrice = product.getDiscountedPrice();

        // Assert
        assertEquals(product.getPreco(), discountedPrice);
    }

    @Test
    void testCalcularDiscountedPriceComDesconto() {
        // Arrange
        product.setDescontoPercentual(20.0); // 20% de desconto

        // Act
        BigDecimal discountedPrice = product.getDiscountedPrice();

        // Assert
        BigDecimal expectedPrice = product.getPreco().multiply(BigDecimal.valueOf(0.80)); // 100.00 - 20%
        assertEquals(expectedPrice, discountedPrice);
    }

    @Test
    void testCalcularDiscountedPriceComDescontoNulo() {
        // Arrange
        product.setDescontoPercentual(null); // Desconto nulo

        // Act
        BigDecimal discountedPrice = product.getDiscountedPrice();

        // Assert
        assertEquals(product.getPreco(), discountedPrice);
    }

    @Test
    void testSettersAndGetters() {
        // Testando os getters e setters
        product.setNome("Novo Nome");
        assertEquals("Novo Nome", product.getNome());

        product.setDescricao("Nova Descrição");
        assertEquals("Nova Descrição", product.getDescricao());

        product.setPreco(BigDecimal.valueOf(150.00));
        assertEquals(BigDecimal.valueOf(150.00), product.getPreco());

        product.setEstoque(20);
        assertEquals(20, product.getEstoque());
    }
}
