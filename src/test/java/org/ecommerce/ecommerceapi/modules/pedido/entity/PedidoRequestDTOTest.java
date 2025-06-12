package org.ecommerce.ecommerceapi.modules.pedido.entity;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void testGettersAndSetters() {
        Pedido pedido = new Pedido();

        pedido.setId(1L);
        pedido.setCancelado(true);
        pedido.setTotal(BigDecimal.valueOf(99.99));
        pedido.setDateCreate(LocalDateTime.now());

        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(10L);
        cliente.setNome("Cliente Teste");
        pedido.setCliente(cliente);

        ItemPedido item = new ItemPedido();
        pedido.setItens(List.of(item));

        // Asserts
        assertEquals(1L, pedido.getId());
        assertTrue(pedido.isCancelado());
        assertEquals(BigDecimal.valueOf(99.99), pedido.getTotal());
        assertNotNull(pedido.getDateCreate());
        assertNotNull(pedido.getCliente());
        assertEquals(10L, pedido.getCliente().getId());
        assertNotNull(pedido.getItens());
        assertEquals(1, pedido.getItens().size());
    }

    @Test
void testEqualsHashCodeToStringAndCanEqual() {
    Pedido pedido1 = new Pedido();
    pedido1.setId(1L);
    pedido1.setTotal(BigDecimal.valueOf(100.00));

    Pedido pedido2 = new Pedido();
    pedido2.setId(1L);
    pedido2.setTotal(BigDecimal.valueOf(100.00));

    Pedido pedido3 = new Pedido();
    pedido3.setId(2L);
    pedido3.setTotal(BigDecimal.valueOf(200.00));

    // equals e hashCode
    assertEquals(pedido1, pedido2);
    assertNotEquals(pedido1, pedido3);
    assertEquals(pedido1.hashCode(), pedido2.hashCode());

    // toString
    String str = pedido1.toString();
    assertNotNull(str);
    assertTrue(str.contains("Pedido"));

    // canEqual (indiretamente via equals)
    assertTrue(pedido1.canEqual(pedido2));
}


}
