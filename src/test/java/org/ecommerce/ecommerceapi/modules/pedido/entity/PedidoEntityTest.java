package org.ecommerce.ecommerceapi.modules.pedido.entity;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoEntityTest {

    @Test
    void deveCriarPedidoValido() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCancelado(false);
        pedido.setTotal(BigDecimal.TEN);
        pedido.setDateCreate(LocalDateTime.now());
        pedido.setCliente(new ClienteEntity());

        assertEquals(1L, pedido.getId());
        assertFalse(pedido.isCancelado());
        assertEquals(BigDecimal.TEN, pedido.getTotal());
        assertNotNull(pedido.getDateCreate());
        assertNotNull(pedido.getCliente());
    }
}
