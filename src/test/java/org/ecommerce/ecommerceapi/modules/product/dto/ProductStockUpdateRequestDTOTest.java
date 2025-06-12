package org.ecommerce.ecommerceapi.modules.product.dto;

import org.ecommerce.ecommerceapi.modules.product.enums.OperacaoEstoque;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductStockUpdateRequestDTOTest {

    @Test
    void testGettersAndSetters() {
        ProductStockUpdateRequestDTO dto = new ProductStockUpdateRequestDTO();

        dto.setOperacao(OperacaoEstoque.AUMENTAR);
        dto.setQuantidade(10);

        assertEquals(OperacaoEstoque.AUMENTAR, dto.getOperacao());
        assertEquals(10, dto.getQuantidade());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductStockUpdateRequestDTO dto1 = new ProductStockUpdateRequestDTO();
        dto1.setOperacao(OperacaoEstoque.AUMENTAR);
        dto1.setQuantidade(10);

        ProductStockUpdateRequestDTO dto2 = new ProductStockUpdateRequestDTO();
        dto2.setOperacao(OperacaoEstoque.AUMENTAR);
        dto2.setQuantidade(10);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        ProductStockUpdateRequestDTO dto = new ProductStockUpdateRequestDTO();
        dto.setOperacao(OperacaoEstoque.AUMENTAR);
        dto.setQuantidade(10);

        String expectedString = "ProductStockUpdateRequestDTO{operacao=AUMENTAR, quantidade=10}";
        assertEquals(expectedString, dto.toString());
    }

    @Test
    void testNullValues() {
        ProductStockUpdateRequestDTO dto = new ProductStockUpdateRequestDTO();

        dto.setOperacao(null);
        dto.setQuantidade(null);

        assertNull(dto.getOperacao());
        assertNull(dto.getQuantidade());
    }
    @Test
    public void testEquals_SameObject() {
        ProductStockUpdateRequestDTO dto = new ProductStockUpdateRequestDTO();
        dto.setOperacao(OperacaoEstoque.AUMENTAR);
        dto.setQuantidade(10);
        
        // Chamando equals com o mesmo objeto
        assertTrue(dto.equals(dto), "Should be equal to itself");
    }

    @Test
    public void testEquals_SimilarObjects() {
        ProductStockUpdateRequestDTO dto1 = new ProductStockUpdateRequestDTO();
        dto1.setOperacao(OperacaoEstoque.AUMENTAR);
        dto1.setQuantidade(10);
        
        ProductStockUpdateRequestDTO dto2 = new ProductStockUpdateRequestDTO();
        dto2.setOperacao(OperacaoEstoque.AUMENTAR);
        dto2.setQuantidade(10);
        
        // Chamando equals com objetos semelhantes
        assertTrue(dto1.equals(dto2), "Similar objects should be equal");
    }

    @Test
    public void testEquals_DifferentObjects() {
        ProductStockUpdateRequestDTO dto1 = new ProductStockUpdateRequestDTO();
        dto1.setOperacao(OperacaoEstoque.AUMENTAR);
        dto1.setQuantidade(10);
        
        ProductStockUpdateRequestDTO dto2 = new ProductStockUpdateRequestDTO();
        dto2.setOperacao(OperacaoEstoque.REDUZIR);
        dto2.setQuantidade(10);
        
        // Chamando equals com objetos diferentes
        assertFalse(dto1.equals(dto2), "Different objects should not be equal");
    }

    @Test
    public void testEquals_Null() {
        ProductStockUpdateRequestDTO dto = new ProductStockUpdateRequestDTO();
        dto.setOperacao(OperacaoEstoque.AUMENTAR);
        dto.setQuantidade(10);
        
        // Chamando equals com null
        assertFalse(dto.equals(null), "Should not be equal to null");
    }

    @Test
    public void testEquals_DifferentClass() {
        ProductStockUpdateRequestDTO dto = new ProductStockUpdateRequestDTO();
        dto.setOperacao(OperacaoEstoque.AUMENTAR);
        dto.setQuantidade(10);
        
        // Chamando equals com objeto de classe diferente
        assertFalse(dto.equals("String"), "Should not be equal to an object of a different class");
    }
}
