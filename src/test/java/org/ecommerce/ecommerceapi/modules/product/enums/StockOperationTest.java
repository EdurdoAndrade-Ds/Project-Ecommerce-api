package org.ecommerce.ecommerceapi.modules.product.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockOperationTest {

    @Test
    void testEnumValues() {
        // Verifica se os valores do enum estão corretos
        assertEquals("AUMENTAR", StockOperation.AUMENTAR.name());
        assertEquals("REDUZIR", StockOperation.REDUZIR.name());
    }

    @Test
    void testEnumCount() {
        // Verifica se o número de valores no enum está correto
        assertEquals(2, StockOperation.values().length);
    }

    @Test
    void testEnumOrdinal() {
        // Verifica os ordinais dos enums
        assertEquals(0, StockOperation.AUMENTAR.ordinal());
        assertEquals(1, StockOperation.REDUZIR.ordinal());
    }
}
