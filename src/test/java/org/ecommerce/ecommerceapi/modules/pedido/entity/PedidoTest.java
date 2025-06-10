package org.ecommerce.ecommerceapi.modules.pedido.entity;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void testGettersAndSetters() {
        Pedido pedido = new Pedido();

        pedido.setId(1L);
        pedido.setCancelado(false);
        pedido.setTotal(BigDecimal.valueOf(100.50));
        LocalDateTime now = LocalDateTime.now();
        pedido.setDateCreate(now);

        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(10L);
        pedido.setCliente(cliente);

        List<ItemPedido> itens = new ArrayList<>();
        pedido.setItens(itens);

        assertEquals(1L, pedido.getId());
        assertFalse(pedido.isCancelado());
        assertEquals(BigDecimal.valueOf(100.50), pedido.getTotal());
        assertEquals(now, pedido.getDateCreate());
        assertEquals(cliente, pedido.getCliente());
        assertEquals(itens, pedido.getItens());
    }

    @Test
    void testEqualsAndHashCode() {
        Pedido pedido1 = new Pedido();
        pedido1.setId(1L);

        Pedido pedido2 = new Pedido();
        pedido2.setId(1L);

        Pedido pedido3 = new Pedido();
        pedido3.setId(2L);

        assertEquals(pedido1, pedido2);
        assertEquals(pedido1.hashCode(), pedido2.hashCode());

        assertNotEquals(pedido1, pedido3);
    }

    @Test
    void testToString() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCancelado(false);
        pedido.setTotal(BigDecimal.valueOf(100.50));
        pedido.setDateCreate(LocalDateTime.of(2025, 6, 10, 12, 0));

        String str = pedido.toString();
        System.out.println(str);  // Imprime a saída para você ver o formato real

        assertTrue(str.contains("id=1"));
        assertTrue(str.contains("cancelado=false"));
        assertTrue(str.contains("total=100.5"));
    }

    @Test
    void testCanEqual() {
        Pedido pedido = new Pedido();
        assertTrue(pedido.canEqual(new Pedido()));
        assertFalse(pedido.canEqual("uma string"));
    }
}
