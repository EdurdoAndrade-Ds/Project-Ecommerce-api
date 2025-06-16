package org.ecommerce.ecommerceapi.modules.pedido.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoStatusTest {

    @Test
    void testValues() {
        PedidoStatus[] values = PedidoStatus.values();
        assertNotNull(values);
        assertEquals(4, values.length); // CRIADO, PAGO, ENVIADO, CANCELADO

        assertEquals(PedidoStatus.CRIADO, values[0]);
        assertEquals(PedidoStatus.PAGO, values[1]);
        assertEquals(PedidoStatus.ENVIADO, values[2]);
        assertEquals(PedidoStatus.CANCELADO, values[3]);
    }

    @Test
    void testValueOf() {
        assertEquals(PedidoStatus.CRIADO, PedidoStatus.valueOf("CRIADO"));
        assertEquals(PedidoStatus.PAGO, PedidoStatus.valueOf("PAGO"));
        assertEquals(PedidoStatus.ENVIADO, PedidoStatus.valueOf("ENVIADO"));
        assertEquals(PedidoStatus.CANCELADO, PedidoStatus.valueOf("CANCELADO"));

        // Testa exceção se nome inválido
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PedidoStatus.valueOf("INVALIDO");
        });
        assertTrue(exception.getMessage().contains("No enum constant"));
    }
}