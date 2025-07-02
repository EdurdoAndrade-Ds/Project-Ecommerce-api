package org.ecommerce.ecommerceapi.modules.pedido.entity;

import org.ecommerce.ecommerceapi.modules.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ItemPedidoTest {

    private ItemPedido itemPedido;
    private Product product;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        itemPedido = new ItemPedido();
        product = new Product();
        product.setId(1L);
        product.setNome("Produto A");
        pedido = new Pedido();
        pedido.setId(10L);
        itemPedido.setId(1L);
        itemPedido.setNomeProduto("Produto A");
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoUnitario(BigDecimal.valueOf(50.00));
        itemPedido.setDiscountPrice(BigDecimal.valueOf(45.00));
        itemPedido.setPrecoPago(BigDecimal.valueOf(90.00));
        itemPedido.setProduto(product);
        itemPedido.setPedido(pedido);
    }

    @Test
    void testGettersAndSetters() {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(1L);
        itemPedido.setNomeProduto("Produto A");
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoUnitario(BigDecimal.valueOf(50.00));
        itemPedido.setDiscountPrice(BigDecimal.valueOf(45.00));
        itemPedido.setPrecoPago(BigDecimal.valueOf(90.00));

        assertEquals(1L, itemPedido.getId());
        assertEquals("Produto A", itemPedido.getNomeProduto());
        assertEquals(2, itemPedido.getQuantidade());
        assertEquals(BigDecimal.valueOf(50.00), itemPedido.getPrecoUnitario());
        assertEquals(BigDecimal.valueOf(45.00), itemPedido.getDiscountPrice());
        assertEquals(BigDecimal.valueOf(90.00), itemPedido.getPrecoPago());
    }
    @Test
    void testGetPedido() {
        assertEquals(pedido, itemPedido.getPedido());
    }

    @Test
    void testToStringContainsFields() {
        String toString = itemPedido.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nomeProduto=Produto A"));
        assertTrue(toString.contains("quantidade=2"));
        assertTrue(toString.contains("precoUnitario=50.00") || toString.contains("precoUnitario=50.0"));
        assertTrue(toString.contains("discountPrice=45.00") || toString.contains("discountPrice=45.0"));
        assertTrue(toString.contains("precoPago=90.00") || toString.contains("precoPago=90.0"));
    }
    
    @Test
    void testEqualsAndHashCode() {
        ItemPedido item1 = new ItemPedido();
        item1.setId(1L);
        item1.setNomeProduto("Produto A");
        item1.setQuantidade(2);
        item1.setPrecoUnitario(BigDecimal.valueOf(50.00));
        item1.setDiscountPrice(BigDecimal.valueOf(45.00));
        item1.setPrecoPago(BigDecimal.valueOf(90.00));

        ItemPedido item2 = new ItemPedido();
        item2.setId(1L);
        item2.setNomeProduto("Produto A");
        item2.setQuantidade(2);
        item2.setPrecoUnitario(BigDecimal.valueOf(50.00));
        item2.setDiscountPrice(BigDecimal.valueOf(45.00));
        item2.setPrecoPago(BigDecimal.valueOf(90.00));

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());

        item2.setQuantidade(3); // Alterando para testar desigualdade
        assertNotEquals(item1, item2);
    }

    @Test
    void testEqualsWithDifferentObjects() {
        ItemPedido item1 = new ItemPedido();
        item1.setId(1L);
        item1.setNomeProduto("Produto A");

        ItemPedido item2 = new ItemPedido();
        item2.setId(2L);
        item2.setNomeProduto("Produto B");

        assertNotEquals(item1, item2);
    }

    @Test
    void testEqualsWithNull() {
        ItemPedido item = new ItemPedido();
        item.setId(1L);
        item.setNomeProduto("Produto A");

        assertNotEquals(item, null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        ItemPedido item = new ItemPedido();
        item.setId(1L);
        item.setNomeProduto("Produto A");

        String differentClassObject = "This is a string";
        assertNotEquals(item, differentClassObject);
    }
}
