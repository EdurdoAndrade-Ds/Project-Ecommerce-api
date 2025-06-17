package org.ecommerce.ecommerceapi.modules.pedido.entity;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Pedido pedido1;
    private Pedido pedido2;
    private ClienteEntity cliente;

    @BeforeEach
    void setUp() {
        cliente = new ClienteEntity();
        cliente.setId(1L);

        pedido1 = new Pedido();
        pedido1.setId(10L);
        pedido1.setCancelado(false);
        pedido1.setTotal(BigDecimal.valueOf(100));
        pedido1.setDateCreate(LocalDateTime.now());
        pedido1.setCliente(cliente);
        pedido1.setItens(new ArrayList<>());

        pedido2 = new Pedido();
        pedido2.setId(10L);
        pedido2.setCancelado(false);
        pedido2.setTotal(BigDecimal.valueOf(100));
        pedido2.setDateCreate(pedido1.getDateCreate()); // mesma data para garantir igualdade
        pedido2.setCliente(cliente);
        pedido2.setItens(new ArrayList<>());
    }

    @Test
    void testEquals_sameObject() {
        assertEquals(pedido1, pedido1);
    }

    @Test
    void testEquals_equalObjects() {
        assertEquals(pedido1, pedido2);
        assertTrue(pedido1.canEqual(pedido2));
        assertEquals(pedido1.hashCode(), pedido2.hashCode());
    }

    @Test
    void testEquals_differentObject() {
        Pedido outroPedido = new Pedido();
        outroPedido.setId(20L);
        assertNotEquals(pedido1, outroPedido);
    }

    @Test
    void testEquals_differentType() {
        assertNotEquals(pedido1, "alguma String");
    }

    @Test
    void testEquals_null() {
        assertNotEquals(pedido1, null);
    }

    @Test
    void testCanEqual() {
        assertTrue(pedido1.canEqual(new Pedido()));
        assertFalse(pedido1.canEqual(new Object()));
    }

    @Test
    void testHashCode_consistency() {
        int hash1 = pedido1.hashCode();
        int hash2 = pedido1.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void testToString_containsFields() {
        String toString = pedido1.toString();
        assertTrue(toString.contains("id=" + pedido1.getId()));
        assertTrue(toString.contains("cancelado=" + pedido1.isCancelado()));
        assertTrue(toString.contains("total=" + pedido1.getTotal().toString()));
        assertTrue(toString.contains("cliente="));
        assertTrue(toString.contains("itens="));
    }
}
