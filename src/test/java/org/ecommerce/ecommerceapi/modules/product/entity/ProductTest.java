package org.ecommerce.ecommerceapi.modules.product.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Produto Teste");
        product.setDescricao("Descrição do Produto Teste");
        product.setPrice(BigDecimal.valueOf(100.00));
        product.setStock(10);
    }

    @Test
    void testCalcularDiscountedPriceSemDesconto() {
        // Arrange
        product.setDiscountPercentage(new BigDecimal("0.00"));

        // Act
        BigDecimal discountedPrice = product.getDiscountedPrice();

        // Assert (alinhe escala para evitar falha por 100 != 100.00)
        BigDecimal expected = product.getPrice().setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, discountedPrice);
    }

    @Test
    void testCalcularDiscountedPriceComDesconto() {
        // Arrange
        product.setDiscountPercentage(new BigDecimal("20.00")); // 20%

        // Act
        BigDecimal discountedPrice = product.getDiscountedPrice();

        // Assert (100.00 * 0.80 = 80.00)
        BigDecimal expected = product.getPrice()
                .multiply(new BigDecimal("0.80"))
                .setScale(2, RoundingMode.HALF_UP);

        assertEquals(expected, discountedPrice);
    }

    @Test
    void testCalcularDiscountedPriceComDescontoNulo() {
        // Arrange
        product.setDiscountPercentage(null); // Desconto nulo

        // Act
        BigDecimal discountedPrice = product.getDiscountedPrice();

        // Assert
        assertEquals(product.getPrice(), discountedPrice);
    }

    @Test
    void testSettersAndGetters() {
        // Testando os getters e setters
        product.setName("Novo Nome");
        assertEquals("Novo Nome", product.getName());

        product.setDescricao("Nova Descrição");
        assertEquals("Nova Descrição", product.getDescricao());

        product.setPrice(BigDecimal.valueOf(150.00));
        assertEquals(BigDecimal.valueOf(150.00), product.getPrice());

        product.setStock(20);
        assertEquals(20, product.getStock());
    }
}
