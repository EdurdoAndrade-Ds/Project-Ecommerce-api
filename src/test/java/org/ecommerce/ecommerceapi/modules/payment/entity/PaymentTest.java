package org.ecommerce.ecommerceapi.modules.payment.entity;

import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private Payment payment;
    private Pedido pedido;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setId(1L);

        payment = new Payment();
        payment.setId(1L);
        payment.setPedido(pedido);
        payment.setValor(BigDecimal.TEN);
        now = LocalDateTime.now();
        payment.setDataPagamento(now);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, payment.getId());
        assertEquals(pedido, payment.getPedido());
        assertEquals(BigDecimal.TEN, payment.getValor());
        assertEquals(now, payment.getDataPagamento());
    }

    @Test
    void testEqualsAndHashCode() {
        Payment other = new Payment();
        other.setId(1L);
        other.setPedido(pedido);
        other.setValor(BigDecimal.TEN);
        other.setDataPagamento(now);

        assertEquals(payment, other);
        assertEquals(payment.hashCode(), other.hashCode());

        other.setValor(BigDecimal.ONE);
        assertNotEquals(payment, other);
    }

    @Test
    void testToString() {
        String str = payment.toString();
        assertNotNull(str);
        assertTrue(str.contains("id=1"));
    }
}
