package org.ecommerce.ecommerceapi.modules.payment.entity;

import org.ecommerce.ecommerceapi.modules.order.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private Payment payment;
    private Order order;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);

        payment = new Payment();
        payment.setId(1L);
        payment.setOrder(order);
        payment.setPrice(BigDecimal.TEN);
        now = LocalDateTime.now();
        payment.setDatePayment(now);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, payment.getId());
        assertEquals(order, payment.getOrder());
        assertEquals(BigDecimal.TEN, payment.getPrice());
        assertEquals(now, payment.getDatePayment());
    }

    @Test
    void testEqualsAndHashCode() {
        Payment other = new Payment();
        other.setId(1L);
        other.setOrder(order);
        other.setPrice(BigDecimal.TEN);
        other.setDatePayment(now);

        assertEquals(payment, other);
        assertEquals(payment.hashCode(), other.hashCode());

        other.setPrice(BigDecimal.ONE);
        assertNotEquals(payment, other);
    }

    @Test
    void testToString() {
        String str = payment.toString();
        assertNotNull(str);
        assertTrue(str.contains("id=1"));
    }
}
