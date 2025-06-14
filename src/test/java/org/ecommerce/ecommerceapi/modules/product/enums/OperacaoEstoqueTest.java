package org.ecommerce.ecommerceapi.modules.product.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperacaoEstoqueTest {

    @Test
    void testEnumValues() {
        // Verifica se os valores do enum estão corretos
        assertEquals("AUMENTAR", OperacaoEstoque.AUMENTAR.name());
        assertEquals("REDUZIR", OperacaoEstoque.REDUZIR.name());
    }

    @Test
    void testEnumCount() {
        // Verifica se o número de valores no enum está correto
        assertEquals(2, OperacaoEstoque.values().length);
    }

    @Test
    void testEnumOrdinal() {
        // Verifica os ordinais dos enums
        assertEquals(0, OperacaoEstoque.AUMENTAR.ordinal());
        assertEquals(1, OperacaoEstoque.REDUZIR.ordinal());
    }
}
