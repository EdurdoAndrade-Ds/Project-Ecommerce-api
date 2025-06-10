package org.ecommerce.ecommerceapi.modules.pedido.entity;

import org.ecommerce.ecommerceapi.modules.product.entities.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TestItemPedido {

    @Test
    void testSetAndGetProduto() {
        ItemPedido item = new ItemPedido();
        Product produto = new Product();
        produto.setId(1L); // Supondo que Product tenha setId()
        produto.setNome("Produto Teste");

        item.setProduto(produto);

        assertNotNull(item.getProduto());
        assertEquals(1L, item.getProduto().getId());
        assertEquals("Produto Teste", item.getProduto().getNome());
    }
}
