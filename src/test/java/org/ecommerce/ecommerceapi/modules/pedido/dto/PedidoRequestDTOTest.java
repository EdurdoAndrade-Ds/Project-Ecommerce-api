package org.ecommerce.ecommerceapi.modules.pedido.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PedidoRequestDTOEqualsCanEqualTest {

    @Test
    void testPedidoRequestDTOEqualsAndCanEqual() {
        PedidoRequestDTO pedido1 = new PedidoRequestDTO();
        PedidoRequestDTO pedido2 = new PedidoRequestDTO();

        // Inicializa itens iguais
        PedidoRequestDTO.ItemDTO item1 = new PedidoRequestDTO.ItemDTO();
        item1.setProdutoId(1L);
        item1.setQuantidade(2);

        PedidoRequestDTO.ItemDTO item2 = new PedidoRequestDTO.ItemDTO();
        item2.setProdutoId(1L);
        item2.setQuantidade(2);

        pedido1.setItens(Arrays.asList(item1));
        pedido2.setItens(Arrays.asList(item2));

        // equals deve retornar true para objetos iguais
        assertTrue(pedido1.equals(pedido2));
        // canEqual deve retornar true para instância da mesma classe
        assertTrue(pedido1.canEqual(pedido2));

        // equals deve retornar false para null e objetos de outras classes
        assertFalse(pedido1.equals(null));
        assertFalse(pedido1.equals(new Object()));
        assertFalse(pedido1.canEqual(null));
        assertFalse(pedido1.canEqual(new Object()));
    }

    @Test
    void testItemDTOEqualsAndCanEqual() {
        PedidoRequestDTO.ItemDTO item1 = new PedidoRequestDTO.ItemDTO();
        PedidoRequestDTO.ItemDTO item2 = new PedidoRequestDTO.ItemDTO();

        item1.setProdutoId(1L);
        item1.setQuantidade(2);

        item2.setProdutoId(1L);
        item2.setQuantidade(2);

        // equals deve retornar true para objetos iguais
        assertTrue(item1.equals(item2));
        // canEqual deve retornar true para instância da mesma classe
        assertTrue(item1.canEqual(item2));

        // equals deve retornar false para null e objetos de outras classes
        assertFalse(item1.equals(null));
        assertFalse(item1.equals(new Object()));
        assertFalse(item1.canEqual(null));
        assertFalse(item1.canEqual(new Object()));
    }
}

