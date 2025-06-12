package org.ecommerce.ecommerceapi.modules.pedido.entity;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    
    @Test
    void criarPedidoComDadosBasicos() {
        Pedido pedido = new Pedido();

        pedido.setId(1L);
        pedido.setCancelado(false);
        pedido.setTotal(new BigDecimal("150.00"));
        pedido.setDateCreate(LocalDateTime.now());

        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(1L);
        cliente.setNome("João Teste");
        pedido.setCliente(cliente);

        ItemPedido item = new ItemPedido();
        item.setId(1L);
        item.setQuantidade(2);
        item.setPedido(pedido);

        pedido.setItens(List.of(item));

        assertEquals(1L, pedido.getId());
        assertFalse(pedido.isCancelado());
        assertEquals(new BigDecimal("150.00"), pedido.getTotal());
        assertNotNull(pedido.getDateCreate());
        assertNotNull(pedido.getCliente());
        assertEquals(1L, pedido.getCliente().getId());
        assertEquals(1, pedido.getItens().size());
        assertEquals(2, pedido.getItens().get(0).getQuantidade());
    }

    
}
