package org.ecommerce.ecommerceapi.modules.pedido.entity;

import org.ecommerce.ecommerceapi.modules.product.entities.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TestItemPedido {

    @Test
    void testGettersAndSetters() {
        ItemPedido item = new ItemPedido();

        item.setId(1L);
        item.setNomeProduto("Produto Teste");
        item.setQuantidade(5);
        item.setPrecoUnitario(BigDecimal.valueOf(19.99));

        Pedido pedido = new Pedido();
        pedido.setId(10L);
        item.setPedido(pedido);

        Product produto = new Product();
        produto.setId(100L);
        item.setProduto(produto);

        assertEquals(1L, item.getId());
        assertEquals("Produto Teste", item.getNomeProduto());
        assertEquals(5, item.getQuantidade());
        assertEquals(BigDecimal.valueOf(19.99), item.getPrecoUnitario());
        assertEquals(pedido, item.getPedido());
        assertEquals(produto, item.getProduto());
    }

    @Test
    void testEqualsAndHashCode() {
        ItemPedido item1 = new ItemPedido();
        item1.setId(1L);

        ItemPedido item2 = new ItemPedido();
        item2.setId(1L);

        ItemPedido item3 = new ItemPedido();
        item3.setId(2L);

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
        assertNotEquals(item1, item3);
    }

    @Test
    void testToString() {
        ItemPedido item = new ItemPedido();
        item.setId(1L);
        item.setNomeProduto("Produto Teste");
        item.setQuantidade(5);
        item.setPrecoUnitario(BigDecimal.valueOf(19.99));

        String str = item.toString();
        System.out.println(str); // Para ver saída real e ajustar asserts

        assertTrue(str.contains("id=1"));
        assertTrue(str.contains("nomeProduto=Produto Teste"));
        assertTrue(str.contains("quantidade=5"));
        assertTrue(str.contains("precoUnitario=19.99"));
    }

    @Test
    void testCanEqual() {
        ItemPedido item = new ItemPedido();
        assertTrue(item.canEqual(new ItemPedido()));
        assertFalse(item.canEqual("uma string"));
    }
}
