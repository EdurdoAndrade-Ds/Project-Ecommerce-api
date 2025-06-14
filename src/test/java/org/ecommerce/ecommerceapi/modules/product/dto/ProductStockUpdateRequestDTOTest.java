package org.ecommerce.ecommerceapi.modules.product.dto;

import org.ecommerce.ecommerceapi.modules.product.enums.OperacaoEstoque;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductStockUpdateRequestDTOTest {

    @Test
    void testGettersAndSetters() {
        ProductStockUpdateRequestDTO stockUpdateRequest = new ProductStockUpdateRequestDTO();

        // Define valores
        stockUpdateRequest.setOperacao(OperacaoEstoque.AUMENTAR);
        stockUpdateRequest.setQuantidade(10);

        // Verifica valores atribuídos
        assertEquals(OperacaoEstoque.AUMENTAR, stockUpdateRequest.getOperacao());
        assertEquals(10, stockUpdateRequest.getQuantidade());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductStockUpdateRequestDTO request1 = new ProductStockUpdateRequestDTO();
        ProductStockUpdateRequestDTO request2 = new ProductStockUpdateRequestDTO();

        request1.setOperacao(OperacaoEstoque.AUMENTAR);
        request1.setQuantidade(10);

        request2.setOperacao(OperacaoEstoque.AUMENTAR);
        request2.setQuantidade(10);

        // Testa que os objetos são iguais
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());

        // Modifica um objeto e testa que eles não são mais iguais
        request2.setQuantidade(5);
        assertNotEquals(request1, request2);
    }

    @Test
    void testToString() {
        ProductStockUpdateRequestDTO stockUpdateRequest = new ProductStockUpdateRequestDTO();
        stockUpdateRequest.setOperacao(OperacaoEstoque.REDUZIR);
        stockUpdateRequest.setQuantidade(5);

        String expectedString = "ProductStockUpdateRequestDTO{" +
                "operacao=REDUZIR, " +
                "quantidade=5" +
                '}';
        assertEquals(expectedString, stockUpdateRequest.toString());
    }
}