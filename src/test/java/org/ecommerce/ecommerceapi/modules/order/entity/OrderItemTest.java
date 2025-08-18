package org.ecommerce.ecommerceapi.modules.order.entity;

import org.ecommerce.ecommerceapi.modules.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    private OrderItem orderItem;
    private Product product;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        orderItem = new OrderItem();
        product = new Product();
        product.setId(1L);
        product.setNome("Produto A");
        pedido = new Pedido();
        pedido.setId(10L);
        orderItem.setId(1L);
        orderItem.setNomeProduto("Produto A");
        orderItem.setQuantidade(2);
        orderItem.setPrecoUnitario(BigDecimal.valueOf(50.00));
        orderItem.setDiscountPrice(BigDecimal.valueOf(45.00));
        orderItem.setPrecoPago(BigDecimal.valueOf(90.00));
        orderItem.setProduto(product);
        orderItem.setPedido(pedido);
    }

    @Test
    void testGettersAndSetters() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setNomeProduto("Produto A");
        orderItem.setQuantidade(2);
        orderItem.setPrecoUnitario(BigDecimal.valueOf(50.00));
        orderItem.setDiscountPrice(BigDecimal.valueOf(45.00));
        orderItem.setPrecoPago(BigDecimal.valueOf(90.00));

        assertEquals(1L, orderItem.getId());
        assertEquals("Produto A", orderItem.getNomeProduto());
        assertEquals(2, orderItem.getQuantidade());
        assertEquals(BigDecimal.valueOf(50.00), orderItem.getPrecoUnitario());
        assertEquals(BigDecimal.valueOf(45.00), orderItem.getDiscountPrice());
        assertEquals(BigDecimal.valueOf(90.00), orderItem.getPrecoPago());
    }
    @Test
    void testGetPedido() {
        assertEquals(pedido, orderItem.getPedido());
    }

    @Test
    void testToStringContainsFields() {
        String toString = orderItem.toString();
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
        OrderItem item1 = new OrderItem();
        item1.setId(1L);
        item1.setNomeProduto("Produto A");
        item1.setQuantidade(2);
        item1.setPrecoUnitario(BigDecimal.valueOf(50.00));
        item1.setDiscountPrice(BigDecimal.valueOf(45.00));
        item1.setPrecoPago(BigDecimal.valueOf(90.00));

        OrderItem item2 = new OrderItem();
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
        OrderItem item1 = new OrderItem();
        item1.setId(1L);
        item1.setNomeProduto("Produto A");

        OrderItem item2 = new OrderItem();
        item2.setId(2L);
        item2.setNomeProduto("Produto B");

        assertNotEquals(item1, item2);
    }

    @Test
    void testEqualsWithNull() {
        OrderItem item = new OrderItem();
        item.setId(1L);
        item.setNomeProduto("Produto A");

        assertNotEquals(item, null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        OrderItem item = new OrderItem();
        item.setId(1L);
        item.setNomeProduto("Produto A");

        String differentClassObject = "This is a string";
        assertNotEquals(item, differentClassObject);
    }
}
