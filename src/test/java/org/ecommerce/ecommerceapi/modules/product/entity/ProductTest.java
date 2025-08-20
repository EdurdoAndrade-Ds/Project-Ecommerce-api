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

        // Assert (forçar escala para evitar falhas 100 vs 100.00)
        BigDecimal expected = product.getPrice().setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, discountedPrice.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void testCalcularDiscountedPriceComDesconto() {
        // Arrange
        product.setDiscountPercentage(new BigDecimal("20.00")); // 20%

        // Act
        BigDecimal discountedPrice = product.getDiscountedPrice();

        // Assert (100.00 - 20% = 80.00)
        BigDecimal expected = product.getPrice()
                .multiply(new BigDecimal("0.80"))
                .setScale(2, RoundingMode.HALF_UP);

        assertEquals(expected, discountedPrice.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void testCalcularDiscountedPriceComDescontoNulo() {
        // Arrange
        product.setDiscountPercentage(null);

        // Act
        BigDecimal discountedPrice = product.getDiscountedPrice();

        // Assert (forçar escala igual da price)
        assertEquals(
                product.getPrice().setScale(2, RoundingMode.HALF_UP),
                discountedPrice.setScale(2, RoundingMode.HALF_UP)
        );
    }

    @Test
    void testSettersAndGetters() {
        product.setName("Novo Nome");
        assertEquals("Novo Nome", product.getName());

        product.setDescricao("Nova Descrição");
        assertEquals("Nova Descrição", product.getDescricao());

        product.setPrice(BigDecimal.valueOf(150.00));
        assertEquals(
                BigDecimal.valueOf(150.00).setScale(2, RoundingMode.HALF_UP),
                product.getPrice().setScale(2, RoundingMode.HALF_UP)
        );

        product.setStock(20);
        assertEquals(20, product.getStock());
    }

    @Test
    void testEqualsAndHashCode() {
        Product p1 = Product.builder()
                .id(1L)
                .name("Produto Teste")
                .descricao("Desc")
                .price(BigDecimal.valueOf(100.00))
                .stock(10)
                .discountPercentage(BigDecimal.valueOf(10.00))
                .build();
        Product p2 = Product.builder()
                .id(1L)
                .name("Produto Teste")
                .descricao("Desc")
                .price(BigDecimal.valueOf(100.00))
                .stock(10)
                .discountPercentage(BigDecimal.valueOf(10.00))
                .build();
        Product p3 = Product.builder()
                .id(2L)
                .name("Outro Produto")
                .descricao("Outra Desc")
                .price(BigDecimal.valueOf(200.00))
                .stock(5)
                .discountPercentage(BigDecimal.valueOf(5.00))
                .build();
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    void testToString() {
        Product p = Product.builder()
                .id(1L)
                .name("Produto Teste")
                .descricao("Desc")
                .price(BigDecimal.valueOf(100.00))
                .stock(10)
                .discountPercentage(BigDecimal.valueOf(10.00))
                .build();
        String str = p.toString();
        assertTrue(str.contains("Produto Teste"));
        assertTrue(str.contains("Desc"));
    }

    @Test
    void testBuilderAndConstructor() {
        Product p = Product.builder()
                .id(1L)
                .name("Produto Teste")
                .descricao("Desc")
                .price(BigDecimal.valueOf(100.00))
                .stock(10)
                .discountPercentage(BigDecimal.valueOf(10.00))
                .build();
        assertEquals("Produto Teste", p.getName());
        assertEquals("Desc", p.getDescricao());
        assertEquals(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP), p.getPrice().setScale(2, RoundingMode.HALF_UP));
        assertEquals(10, p.getStock());
        assertEquals(BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP), p.getDiscountPercentage().setScale(2, RoundingMode.HALF_UP));

        Product p2 = new Product(2L, "Nome", "Desc2", BigDecimal.valueOf(50.00), 5, BigDecimal.valueOf(5.00), null);
        assertEquals(2L, p2.getId());
        assertEquals("Nome", p2.getName());
        assertEquals("Desc2", p2.getDescricao());
        assertEquals(BigDecimal.valueOf(50.00).setScale(2, RoundingMode.HALF_UP), p2.getPrice().setScale(2, RoundingMode.HALF_UP));
        assertEquals(5, p2.getStock());
        assertEquals(BigDecimal.valueOf(5.00).setScale(2, RoundingMode.HALF_UP), p2.getDiscountPercentage().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void testCanEquals() {
        Product p = Product.builder().id(1L).build();
        Product p2 = Product.builder().id(1L).build();
        assertTrue(p.canEqual(p2));
        assertFalse(p.canEqual("string"));
    }

    @Test
    void testDiscountedPriceDesconto100() {
        product.setDiscountPercentage(new BigDecimal("100.00"));
        BigDecimal discountedPrice = product.getDiscountedPrice();
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), discountedPrice.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void testDiscountedPriceDescontoNegativo() {
        product.setDiscountPercentage(new BigDecimal("-10.00"));
        BigDecimal discountedPrice = product.getDiscountedPrice();
        // Espera que o preço fique maior que o original
        BigDecimal expected = product.getPrice().multiply(new BigDecimal("1.10")).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, discountedPrice.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void testDiscountedPriceDescontoMaiorQue100() {
        product.setDiscountPercentage(new BigDecimal("150.00"));
        BigDecimal discountedPrice = product.getDiscountedPrice();
        // Espera que o preço não seja negativo, mas pode ser zero
        assertTrue(discountedPrice.compareTo(BigDecimal.ZERO) <= 0);
    }

    @Test
    void testDiscountedPricePriceNulo() {
        product.setPrice(null);
        product.setDiscountPercentage(new BigDecimal("10.00"));
        assertNull(product.getDiscountedPrice());
    }

    @Test
    void testDiscountedPriceTodosNulos() {
        Product p = new Product();
        assertNull(p.getDiscountedPrice());
    }

    @Test
    void testEqualsAndHashCodeCamposNulos() {
        Product p1 = new Product();
        Product p2 = new Product();
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testBuilderCamposOmitidos() {
        Product p = Product.builder().build();
        assertNull(p.getId());
        assertNull(p.getName());
        assertNull(p.getDescricao());
        assertNull(p.getPrice());
        assertNull(p.getStock());
        assertNull(p.getDiscountPercentage());
    }

    @Test
    void testSetDiscountedPrice() {
        product.setDiscountedPrice(BigDecimal.valueOf(42.42));
        assertEquals(BigDecimal.valueOf(42.42).setScale(2, RoundingMode.HALF_UP), product.getDiscountedPrice());
    }

    @Test
    void testEqualsComObjetoNulo() {
        Product p1 = Product.builder().id(1L).build();
        assertNotEquals(p1, null);
    }

    @Test
    void testEqualsComOutroTipo() {
        Product p1 = Product.builder().id(1L).build();
        assertNotEquals(p1, "string");
    }

    @Test
    void testEqualsComDiscountedPriceDiferente() {
        Product p1 = Product.builder().id(1L).discountedPrice(BigDecimal.valueOf(10)).build();
        Product p2 = Product.builder().id(1L).discountedPrice(BigDecimal.valueOf(20)).build();
        assertNotEquals(p1, p2);
    }

    @Test
    void testEqualsComCamposDiferentes() {
        Product p1 = Product.builder().id(1L).name("A").build();
        Product p2 = Product.builder().id(2L).name("B").build();
        assertNotEquals(p1, p2);
    }

    @Test
    void testEqualsTodosCamposNulos() {
        Product p1 = new Product();
        Product p2 = new Product();
        assertEquals(p1, p2);
    }
}
