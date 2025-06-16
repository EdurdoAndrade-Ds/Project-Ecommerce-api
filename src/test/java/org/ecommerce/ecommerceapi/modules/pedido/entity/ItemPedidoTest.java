package org.ecommerce.ecommerceapi.modules.pedido.entity;

import org.ecommerce.ecommerceapi.modules.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ItemPedidoTest {

    private ItemPedido itemPedido;
    private Product product;

    @BeforeEach
    void setUp() {
        itemPedido = new ItemPedido();
        product = new Product(); // Supondo que você tenha um construtor padrão em Product
        product.setId(1L);
        product.setNome("Produto A");
        
        itemPedido.setId(1L);
        itemPedido.setNomeProduto("Produto A");
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoUnitario(BigDecimal.valueOf(50.00)); // Valor fracionado
        itemPedido.setDescountPrice(BigDecimal.valueOf(45.00)); // Valor fracionado
        itemPedido.setPrecoPago(BigDecimal.valueOf(90.00)); // Valor fracionado
        itemPedido.setProduto(product);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, itemPedido.getId());
        assertEquals("Produto A", itemPedido.getNomeProduto());
        assertEquals(2, itemPedido.getQuantidade());
        assertTrue(itemPedido.getPrecoUnitario().compareTo(BigDecimal.valueOf(50.00)) == 0);
        assertTrue(itemPedido.getDescountPrice().compareTo(BigDecimal.valueOf(45.00)) == 0);
        assertTrue(itemPedido.getPrecoPago().compareTo(BigDecimal.valueOf(90.00)) == 0);
        assertEquals(product, itemPedido.getProduto());
    }

    @Test
    void testEqualsAndHashCode() {
        ItemPedido anotherItemPedido = new ItemPedido();
        anotherItemPedido.setId(1L);
        anotherItemPedido.setNomeProduto("Produto A");
        anotherItemPedido.setQuantidade(2);
        anotherItemPedido.setPrecoUnitario(BigDecimal.valueOf(50.00));
        anotherItemPedido.setDescountPrice(BigDecimal.valueOf(45.00));
        anotherItemPedido.setPrecoPago(BigDecimal.valueOf(90.00));
        anotherItemPedido.setProduto(product);

        assertEquals(itemPedido, anotherItemPedido);
        assertEquals(itemPedido.hashCode(), anotherItemPedido.hashCode());

        anotherItemPedido.setQuantidade(3); // Alterando para testar desigualdade
        assertNotEquals(itemPedido, anotherItemPedido);
    }

    @Test
    void testToString() {
        String expectedString = "ItemPedido(id=1, nomeProduto=Produto A, quantidade=2, precoUnitario=50.00, descountPrice=45.00, precoPago=90.00)";
        assertTrue(itemPedido.toString().contains("nomeProduto=Produto A"));
        assertTrue(itemPedido.toString().contains("quantidade=2"));
    }
}
