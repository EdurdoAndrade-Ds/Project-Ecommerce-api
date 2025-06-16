package org.ecommerce.ecommerceapi.modules.product.dto;

import org.ecommerce.ecommerceapi.modules.product.enums.OperacaoEstoque;
import org.junit.jupiter.api.Test;

import java.util.Objects; // Adicione esta importação

import static org.junit.jupiter.api.Assertions.*;

class ProductStockUpdateRequestDTOTest {

    @Test
    void testEquals() {
        ProductStockUpdateRequestDTO request1 = new ProductStockUpdateRequestDTO();
        request1.setOperacao(OperacaoEstoque.AUMENTAR);
        request1.setQuantidade(10);

        ProductStockUpdateRequestDTO request2 = new ProductStockUpdateRequestDTO();
        request2.setOperacao(OperacaoEstoque.AUMENTAR);
        request2.setQuantidade(10);

        assertEquals(request1, request2); // Deve ser igual

        request2.setQuantidade(5);
        assertNotEquals(request1, request2); // Não deve ser igual
    }

    @Test
    void testHashCode() {
        ProductStockUpdateRequestDTO request = new ProductStockUpdateRequestDTO();
        request.setOperacao(OperacaoEstoque.AUMENTAR);
        request.setQuantidade(10);

        int expectedHashCode = Objects.hash(OperacaoEstoque.AUMENTAR, 10);
        assertEquals(expectedHashCode, request.hashCode());
    }

    @Test
    void testToString() {
        ProductStockUpdateRequestDTO request = new ProductStockUpdateRequestDTO();
        request.setOperacao(OperacaoEstoque.AUMENTAR);
        request.setQuantidade(10);

        String expectedString = "ProductStockUpdateRequestDTO{operacao=AUMENTAR, quantidade=10}";
        assertEquals(expectedString, request.toString());
    }

    @Test
    void testCanEqual() {
        ProductStockUpdateRequestDTO requestDTO = new ProductStockUpdateRequestDTO();
        assertTrue(requestDTO.canEqual(new ProductStockUpdateRequestDTO())); // Deve retornar true
        assertFalse(requestDTO.canEqual(new Object())); // Deve retornar false
    }
}
