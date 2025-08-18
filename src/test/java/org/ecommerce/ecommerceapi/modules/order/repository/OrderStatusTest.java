package org.ecommerce.ecommerceapi.modules.order.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void testValues() {
        OrderStatus[] values = OrderStatus.values();
        assertNotNull(values);
        assertEquals(4, values.length); // CRIADO, PAGO, ENVIADO, CANCELADO

        assertEquals(OrderStatus.CRIADO, values[0]);
        assertEquals(OrderStatus.PAGO, values[1]);
        assertEquals(OrderStatus.ENVIADO, values[2]);
        assertEquals(OrderStatus.CANCELADO, values[3]);
    }

    @Test
    void testValueOf() {
        assertEquals(OrderStatus.CRIADO, OrderStatus.valueOf("CRIADO"));
        assertEquals(OrderStatus.PAGO, OrderStatus.valueOf("PAGO"));
        assertEquals(OrderStatus.ENVIADO, OrderStatus.valueOf("ENVIADO"));
        assertEquals(OrderStatus.CANCELADO, OrderStatus.valueOf("CANCELADO"));

        // Testa exceção se name inválido
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            OrderStatus.valueOf("INVALIDO");
        });
        assertTrue(exception.getMessage().contains("No enum constant"));
    }
}