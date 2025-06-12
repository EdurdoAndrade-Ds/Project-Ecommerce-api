package org.ecommerce.ecommerceapi.modules.pedido.entity;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    
    @Test
    void deveCobrirTodosOsMetodosDoPedido() {
        Pedido pedido1 = new Pedido();
        pedido1.setId(1L);
        pedido1.setCancelado(false);
        pedido1.setTotal(BigDecimal.TEN);
        pedido1.setDateCreate(LocalDateTime.now());
        pedido1.setCliente(new ClienteEntity());
        pedido1.setItens(List.of()); // importante!

        Pedido pedido2 = new Pedido();
        pedido2.setId(1L);
        pedido2.setCancelado(false);
        pedido2.setTotal(BigDecimal.TEN);
        pedido2.setDateCreate(pedido1.getDateCreate());
        pedido2.setCliente(pedido1.getCliente());
        pedido2.setItens(pedido1.getItens());

        // Testando métodos do Lombok
        assertEquals(pedido1, pedido2); // equals
        assertEquals(pedido1.hashCode(), pedido2.hashCode()); // hashCode
        assertNotNull(pedido1.toString()); // toString
    }

}
