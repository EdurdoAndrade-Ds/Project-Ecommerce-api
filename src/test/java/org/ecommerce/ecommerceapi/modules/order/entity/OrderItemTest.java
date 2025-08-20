package org.ecommerce.ecommerceapi.modules.order.entity;

import org.ecommerce.ecommerceapi.modules.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    private OrderItem orderItem;
    private Product product;
    private Order order;

    @BeforeEach
    void setUp() {
        orderItem = new OrderItem();
        product = new Product();
        product.setId(1L);
        product.setName("Product A");
        order = new Order();
        order.setId(10L);
        orderItem.setId(1L);
        orderItem.setNameProduct("Product A");
        orderItem.setQuantity(2);
        orderItem.setUnitPrice(BigDecimal.valueOf(50.00));
        orderItem.setDiscountPrice(BigDecimal.valueOf(45.00));
        orderItem.setPricePad(BigDecimal.valueOf(90.00));
        orderItem.setProduct(product);
        orderItem.setOrder(order);
    }

    @Test
    void testGettersAndSetters() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setNameProduct("Product A");
        orderItem.setQuantity(2);
        orderItem.setUnitPrice(BigDecimal.valueOf(50.00));
        orderItem.setDiscountPrice(BigDecimal.valueOf(45.00));
        orderItem.setPricePad(BigDecimal.valueOf(90.00));

        assertEquals(1L, orderItem.getId());
        assertEquals("Product A", orderItem.getNameProduct());
        assertEquals(2, orderItem.getQuantity());
        assertEquals(BigDecimal.valueOf(50.00), orderItem.getUnitPrice());
        assertEquals(BigDecimal.valueOf(45.00), orderItem.getDiscountPrice());
        assertEquals(BigDecimal.valueOf(90.00), orderItem.getPricePad());
    }
    @Test
    void testGetPedido() {
        assertEquals(order, orderItem.getOrder());
    }

    @Test
    void testToStringContainsFields() {
        String toString = orderItem.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nameProduct=Product A"));
        assertTrue(toString.contains("quantity=2"));
        assertTrue(toString.contains("unitPrice=50.00") || toString.contains("unitPrice=50.0"));
        assertTrue(toString.contains("discountPrice=45.00") || toString.contains("discountPrice=45.0"));
        assertTrue(toString.contains("pricePad=90.00") || toString.contains("pricePad=90.0"));
    }
    
    @Test
    void testEqualsAndHashCode() {
        OrderItem item1 = new OrderItem();
        item1.setId(1L);
        item1.setNameProduct("Product A");
        item1.setQuantity(2);
        item1.setUnitPrice(BigDecimal.valueOf(50.00));
        item1.setDiscountPrice(BigDecimal.valueOf(45.00));
        item1.setPricePad(BigDecimal.valueOf(90.00));

        OrderItem item2 = new OrderItem();
        item2.setId(1L);
        item2.setNameProduct("Product A");
        item2.setQuantity(2);
        item2.setUnitPrice(BigDecimal.valueOf(50.00));
        item2.setDiscountPrice(BigDecimal.valueOf(45.00));
        item2.setPricePad(BigDecimal.valueOf(90.00));

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());

        item2.setQuantity(3); // Alterando para testar desigualdade
        assertNotEquals(item1, item2);
    }

    @Test
    void testEqualsWithDifferentObjects() {
        OrderItem item1 = new OrderItem();
        item1.setId(1L);
        item1.setNameProduct("Product A");

        OrderItem item2 = new OrderItem();
        item2.setId(2L);
        item2.setNameProduct("Product B");

        assertNotEquals(item1, item2);
    }

    @Test
    void testEqualsWithNull() {
        OrderItem item = new OrderItem();
        item.setId(1L);
        item.setNameProduct("Product A");

        assertNotEquals(item, null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        OrderItem item = new OrderItem();
        item.setId(1L);
        item.setNameProduct("Product A");

        String differentClassObject = "This is a string";
        assertNotEquals(item, differentClassObject);
    }

    @Test
    void testEquals_canEqualShouldReturnFalse() {
        // classe fake que herda de OrderItem
        class FakeOrderItem extends OrderItem {}
        OrderItem item = new OrderItem(1L, "Produto", 1,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ONE, null, null);

        FakeOrderItem fake = new FakeOrderItem();
        fake.setId(1L);
        fake.setNameProduct("Produto");
        fake.setQuantity(1);
        fake.setUnitPrice(BigDecimal.TEN);
        fake.setDiscountPrice(BigDecimal.ONE);
        fake.setPricePad(BigDecimal.ONE);

        // força branch do canEqual
        assertNotEquals(item, fake);
    }

    @Test
    void testEquals_withNullOrderOrProduct() {
        OrderItem item1 = new OrderItem(1L, "Produto", 2,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ONE, null, null);

        OrderItem item2 = new OrderItem(1L, "Produto", 2,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ONE, new Order(), new Product());

        assertNotEquals(item1, item2); // cobre null vs non-null
    }

    @Test
    void testHashCode_withNullFields() {
        OrderItem item1 = new OrderItem(1L, null, null,
                null, null, null, null, null);

        OrderItem item2 = new OrderItem(1L, null, null,
                null, null, null, null, null);

        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void testEqualsAndHashCode_sameValues() {
        Product product = new Product();
        product.setId(1L);

        Order order = new Order();
        order.setId(10L);

        OrderItem item1 = OrderItem.builder()
                .id(1L)
                .nameProduct("Produto")
                .quantity(2)
                .unitPrice(BigDecimal.TEN)
                .discountPrice(BigDecimal.ONE)
                .pricePad(BigDecimal.valueOf(18))
                .order(order)
                .product(product)
                .build();

        OrderItem item2 = OrderItem.builder()
                .id(1L)
                .nameProduct("Produto")
                .quantity(2)
                .unitPrice(BigDecimal.TEN)
                .discountPrice(BigDecimal.ONE)
                .pricePad(BigDecimal.valueOf(18))
                .order(order)
                .product(product)
                .build();

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void testEquals_differentValues_shouldNotBeEqual() {
        OrderItem item1 = new OrderItem(1L, "Produto A", 2,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(20), null, null);

        OrderItem item2 = new OrderItem(2L, "Produto B", 3,
                BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, null, null);

        assertNotEquals(item1, item2);
    }

    @Test
    void testEquals_withNullFieldDifference() {
        OrderItem item1 = new OrderItem(1L, null, 2,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(20), null, null);

        OrderItem item2 = new OrderItem(1L, "Produto", 2,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(20), null, null);

        assertNotEquals(item1, item2);
    }

    @Test
    void testEquals_withNullAndDifferentClass() {
        OrderItem item = new OrderItem(1L, "Produto", 2,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(20), null, null);

        assertNotEquals(item, null);
        assertNotEquals(item, "string");
    }

    @Test
    void testEquals_sameReference_shouldBeTrue() {
        OrderItem item = new OrderItem();
        assertEquals(item, item);
    }

    @Test
    void testBuilder_createsObjectCorrectly() {
        OrderItem item = OrderItem.builder()
                .id(5L)
                .nameProduct("Builder Produto")
                .quantity(10)
                .unitPrice(BigDecimal.valueOf(100))
                .discountPrice(BigDecimal.valueOf(20))
                .pricePad(BigDecimal.valueOf(80))
                .build();

        assertEquals(5L, item.getId());
        assertEquals("Builder Produto", item.getNameProduct());
        assertEquals(10, item.getQuantity());
        assertEquals(BigDecimal.valueOf(100), item.getUnitPrice());
        assertEquals(BigDecimal.valueOf(20), item.getDiscountPrice());
        assertEquals(BigDecimal.valueOf(80), item.getPricePad());
    }

    @Test
    void testToString_containsFields() {
        OrderItem item = new OrderItem(1L, "Produto Teste", 3,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(29), null, null);

        String toString = item.toString();
        assertTrue(toString.contains("Produto Teste"));
        assertTrue(toString.contains("29"));
    }
}
