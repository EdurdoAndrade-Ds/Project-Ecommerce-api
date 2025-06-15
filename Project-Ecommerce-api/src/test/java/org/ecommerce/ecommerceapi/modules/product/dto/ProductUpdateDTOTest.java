package org.ecommerce.ecommerceapi.modules.product.dto;
     
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ProductUpdateDTOTest {

    @Test
    void testGettersAndSetters() {
        ProductUpdateDTO product = new ProductUpdateDTO();
        product.setNome("Produto A");
        product.setDescricao("Descrição do Produto A");
        product.setPreco(BigDecimal.valueOf(19.99));

        assertEquals("Produto A", product.getNome());
        assertEquals("Descrição do Produto A", product.getDescricao());
        assertEquals(BigDecimal.valueOf(19.99), product.getPreco());
    }

    @Test
    void testToString() {
        ProductUpdateDTO product = new ProductUpdateDTO();
        product.setNome("Produto A");
        product.setDescricao("Descrição do Produto A");
        product.setPreco(BigDecimal.valueOf(19.99));

        String expectedString = "ProductUpdateDTO{nome='Produto A', descricao='Descrição do Produto A', preco=19.99}";
        assertEquals(expectedString, product.toString());
    }

    @Test
    void testCanEqual() {
        ProductUpdateDTO product = new ProductUpdateDTO();
        assertTrue(product.canEqual(new ProductUpdateDTO()));
        assertFalse(product.canEqual(new Object()));
    }
    
    @Test
    void testEqualsAndHashCode() {
        ProductUpdateDTO product1 = new ProductUpdateDTO();
        product1.setNome("Produto A");
        product1.setDescricao("Descrição do Produto A");
        product1.setPreco(BigDecimal.valueOf(19.99));

        ProductUpdateDTO product2 = new ProductUpdateDTO();
        product2.setNome("Produto A");
        product2.setDescricao("Descrição do Produto A");
        product2.setPreco(BigDecimal.valueOf(19.99));

        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());

        product2.setPreco(BigDecimal.valueOf(29.99));
        assertNotEquals(product1, product2);
    }
}
