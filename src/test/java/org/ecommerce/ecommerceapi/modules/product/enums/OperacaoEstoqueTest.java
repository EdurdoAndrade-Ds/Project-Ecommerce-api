package org.ecommerce.ecommerceapi.modules.product.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperacaoEstoqueTest {

    @Test
    void testOperacaoEstoque() {
        // Verificando os valores da enumeração
        assertEquals("AUMENTAR", OperacaoEstoque.AUMENTAR.name());
        assertEquals("REDUZIR", OperacaoEstoque.REDUZIR.name());

        // Verificando a quantidade de valores na enumeração
        assertEquals(2, OperacaoEstoque.values().length);
    }
}
