package org.ecommerce.ecommerceapi.modules.order.entity;

import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Pedido pedido;
    private ClientEntity cliente;

    @BeforeEach
    void setUp() {
        cliente = new ClientEntity();
        cliente.setId(1L); // Definindo um ID para o cliente

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCancelado(false);
        pedido.setTotal(BigDecimal.valueOf(100.00));
        pedido.setDateCreate(LocalDateTime.now());
        pedido.setCliente(cliente);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, pedido.getId());
        assertFalse(pedido.isCancelado());
        assertEquals(BigDecimal.valueOf(100.00), pedido.getTotal());
        assertNotNull(pedido.getDateCreate());
        assertEquals(cliente, pedido.getCliente());
    }

    @Test
    void testEqualsAndHashCode() {
        Pedido pedido2 = new Pedido();
        pedido2.setId(1L);
        pedido2.setCancelado(false);
        pedido2.setTotal(BigDecimal.valueOf(100.00));
        pedido2.setDateCreate(pedido.getDateCreate());
        pedido2.setCliente(cliente);

        assertEquals(pedido, pedido2);
        assertEquals(pedido.hashCode(), pedido2.hashCode());

        pedido2.setCancelado(true); // Alterando para testar desigualdade
        assertNotEquals(pedido, pedido2);
    }

    @Test
    void testEqualsWithDifferentObjects() {
        Pedido pedido2 = new Pedido();
        pedido2.setId(2L); // ID diferente

        assertNotEquals(pedido, pedido2);
    }

    @Test
    void testEqualsWithNull() {
        assertNotEquals(pedido, null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        String differentClassObject = "This is a string";
        assertNotEquals(pedido, differentClassObject);
    }
}
