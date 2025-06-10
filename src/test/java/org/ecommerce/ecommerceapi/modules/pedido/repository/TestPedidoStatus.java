package org.ecommerce.ecommerceapi.modules.pedido.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestPedidoStatus {

    @Test
    void values() {
        PedidoStatus[] statuses = PedidoStatus.values();
        assertEquals(4, statuses.length);
        assertArrayEquals(new PedidoStatus[]{
            PedidoStatus.CRIADO,
            PedidoStatus.PAGO,
            PedidoStatus.ENVIADO,
            PedidoStatus.CANCELADO
        }, statuses);
    }

    @Test
    void valueOf() {
        assertEquals(PedidoStatus.CRIADO, PedidoStatus.valueOf("CRIADO"));
        assertEquals(PedidoStatus.PAGO, PedidoStatus.valueOf("PAGO"));
        assertEquals(PedidoStatus.ENVIADO, PedidoStatus.valueOf("ENVIADO"));
        assertEquals(PedidoStatus.CANCELADO, PedidoStatus.valueOf("CANCELADO"));

        assertThrows(IllegalArgumentException.class, () -> {
            PedidoStatus.valueOf("INVALIDO");
        });
    }
}
