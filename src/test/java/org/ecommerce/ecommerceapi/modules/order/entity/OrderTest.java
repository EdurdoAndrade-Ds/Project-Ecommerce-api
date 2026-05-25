package org.ecommerce.ecommerceapi.modules.order.entity;

import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;
    private ClientEntity cliente;

    @BeforeEach
    void setUp() {
        cliente = new ClientEntity();
        cliente.setId(1L); // Definindo um ID para o cliente

        order = new Order();
        order.setId(1L);
        order.setCancelado(false);
        order.setTotal(BigDecimal.valueOf(100.00));
        order.setDateCreate(LocalDateTime.now());
        order.setCliente(cliente);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, order.getId());
        assertFalse(order.isCancelado());
        assertEquals(BigDecimal.valueOf(100.00), order.getTotal());
        assertNotNull(order.getDateCreate());
        assertEquals(cliente, order.getCliente());
    }

    @Test
    void testEqualsAndHashCode() {
        Order order2 = new Order();
        order2.setId(1L);
        order2.setCancelado(false);
        order2.setTotal(BigDecimal.valueOf(100.00));
        order2.setDateCreate(order.getDateCreate());
        order2.setCliente(cliente);

        assertEquals(order, order2);
        assertEquals(order.hashCode(), order2.hashCode());

        order2.setCancelado(true); // Alterando para testar desigualdade
        assertNotEquals(order, order2);
    }

    @Test
    void testEqualsWithDifferentObjects() {
        Order order2 = new Order();
        order2.setId(2L); // ID diferente

        assertNotEquals(order, order2);
    }

    @Test
    void testEqualsWithNull() {
        assertNotEquals(order, null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        String differentClassObject = "This is a string";
        assertNotEquals(order, differentClassObject);
    }
}
