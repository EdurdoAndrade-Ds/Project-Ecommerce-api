package org.ecommerce.ecommerceapi.modules.order.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OrderRequestDTOTest {

    @Test
    void testPedidoRequestDTOEqualsAndCanEqual() {
        OrderRequestDTO pedido1 = new OrderRequestDTO();
        OrderRequestDTO pedido2 = new OrderRequestDTO();

        // Inicializa itens iguais
        OrderRequestDTO.ItemDTO item1 = new OrderRequestDTO.ItemDTO();
        item1.setProdutoId(1L);
        item1.setQuantidade(2);

        OrderRequestDTO.ItemDTO item2 = new OrderRequestDTO.ItemDTO();
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
        OrderRequestDTO.ItemDTO item1 = new OrderRequestDTO.ItemDTO();
        OrderRequestDTO.ItemDTO item2 = new OrderRequestDTO.ItemDTO();

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

    @Test
    void testPedidoRequestDTOHashCode() {
        OrderRequestDTO pedido1 = new OrderRequestDTO();
        OrderRequestDTO pedido2 = new OrderRequestDTO();

        // Inicializa itens iguais
        OrderRequestDTO.ItemDTO item1 = new OrderRequestDTO.ItemDTO();
        item1.setProdutoId(1L);
        item1.setQuantidade(2);

        OrderRequestDTO.ItemDTO item2 = new OrderRequestDTO.ItemDTO();
        item2.setProdutoId(1L);
        item2.setQuantidade(2);

        pedido1.setItens(Arrays.asList(item1));
        pedido2.setItens(Arrays.asList(item2));

        // hashCode deve ser igual para objetos iguais
        assertEquals(pedido1.hashCode(), pedido2.hashCode());

        // Modificando um item para que os hashCodes sejam diferentes
        item2.setQuantidade(3);
        pedido2.setItens(Arrays.asList(item2));

        // hashCode deve ser diferente para objetos diferentes
        assertNotEquals(pedido1.hashCode(), pedido2.hashCode());
    }

    @Test
    void testItemDTOHashCode() {
        OrderRequestDTO.ItemDTO item1 = new OrderRequestDTO.ItemDTO();
        OrderRequestDTO.ItemDTO item2 = new OrderRequestDTO.ItemDTO();

        item1.setProdutoId(1L);
        item1.setQuantidade(2);

        item2.setProdutoId(1L);
        item2.setQuantidade(2);

        // hashCode deve ser igual para objetos iguais
        assertEquals(item1.hashCode(), item2.hashCode());

        // Modificando um campo para que os hashCodes sejam diferentes
        item2.setQuantidade(3);

        // hashCode deve ser diferente para objetos diferentes
        assertNotEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void testPedidoRequestDTOToString() {
        OrderRequestDTO pedido = new OrderRequestDTO();
        OrderRequestDTO.ItemDTO item = new OrderRequestDTO.ItemDTO();
        item.setProdutoId(1L);
        item.setQuantidade(2);

        pedido.setItens(Arrays.asList(item));

        String expectedString = "PedidoRequestDTO{itens=[ItemDTO{produtoId=1, quantidade=2}]}";
        assertEquals(expectedString, pedido.toString());
    }

    @Test
    void testItemDTOToString() {
        OrderRequestDTO.ItemDTO item = new OrderRequestDTO.ItemDTO();
        item.setProdutoId(1L);
        item.setQuantidade(2);

        String expectedString = "ItemDTO{produtoId=1, quantidade=2}";
        assertEquals(expectedString, item.toString());
    }
}