package org.ecommerce.ecommerceapi.modules.order.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseDTOTest {

    @Test
    void testEqualsAndHashCode_sameValues_shouldBeEqual() {
        OrderResponseDTO.ItemDTO item1 = OrderResponseDTO.ItemDTO.builder()
                .produtoId(1L)
                .nomeProduto("Produto X")
                .quantidade(2)
                .precoUnitario(BigDecimal.valueOf(10.0))
                .discountPrice(BigDecimal.valueOf(2.0))
                .precoPago(BigDecimal.valueOf(18.0))
                .build();

        OrderResponseDTO.ItemDTO item2 = OrderResponseDTO.ItemDTO.builder()
                .produtoId(1L)
                .nomeProduto("Produto X")
                .quantidade(2)
                .precoUnitario(BigDecimal.valueOf(10.0))
                .discountPrice(BigDecimal.valueOf(2.0))
                .precoPago(BigDecimal.valueOf(18.0))
                .build();

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void testEquals_differentValues_shouldNotBeEqual() {
        OrderResponseDTO.ItemDTO item1 = new OrderResponseDTO.ItemDTO(1L, "Produto X", 2,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(9));

        OrderResponseDTO.ItemDTO item2 = new OrderResponseDTO.ItemDTO(2L, "Produto Y", 3,
                BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE);

        assertNotEquals(item1, item2);
    }

    @Test
    void testEquals_withNullAndDifferentClass() {
        OrderResponseDTO.ItemDTO item = new OrderResponseDTO.ItemDTO(1L, "Produto X", 2,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(9));

        assertNotEquals(item, null);
        assertNotEquals(item, "String qualquer");
    }

    @Test
    void testBuilderAndGetters() {
        OrderResponseDTO.ItemDTO item = OrderResponseDTO.ItemDTO.builder()
                .produtoId(5L)
                .nomeProduto("Produto Builder")
                .quantidade(10)
                .precoUnitario(BigDecimal.valueOf(50))
                .discountPrice(BigDecimal.valueOf(5))
                .precoPago(BigDecimal.valueOf(45))
                .build();

        assertEquals(5L, item.getProdutoId());
        assertEquals("Produto Builder", item.getNomeProduto());
        assertEquals(10, item.getQuantidade());
        assertEquals(BigDecimal.valueOf(50), item.getPrecoUnitario());
        assertEquals(BigDecimal.valueOf(5), item.getDiscountPrice());
        assertEquals(BigDecimal.valueOf(45), item.getPrecoPago());
    }

    @Test
    void testToString_containsFieldValues() {
        OrderResponseDTO.ItemDTO item = new OrderResponseDTO.ItemDTO(1L, "Produto Teste", 3,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(29));

        String toString = item.toString();

        assertTrue(toString.contains("Produto Teste"));
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("3"));
    }

    @Test
    void testEquals_sameReference_shouldBeTrue() {
        OrderResponseDTO.ItemDTO item = new OrderResponseDTO.ItemDTO(1L, "Produto", 2,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(9));

        assertEquals(item, item); // o == this
    }

    @Test
    void testEquals_differentClass_shouldBeFalseDueToCanEqual() {
        class FakeItemDTO extends OrderResponseDTO.ItemDTO {}
        OrderResponseDTO.ItemDTO item = new OrderResponseDTO.ItemDTO(1L, "Produto", 2,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(9));

        FakeItemDTO fake = new FakeItemDTO();
        fake.setProdutoId(1L);
        fake.setNomeProduto("Produto");
        fake.setQuantidade(2);
        fake.setPrecoUnitario(BigDecimal.TEN);
        fake.setDiscountPrice(BigDecimal.ONE);
        fake.setPrecoPago(BigDecimal.valueOf(9));

        assertNotEquals(item, fake); // força branch do canEqual()
    }

    @Test
    void testEquals_oneFieldDifferent_shouldBeFalse() {
        OrderResponseDTO.ItemDTO item1 = new OrderResponseDTO.ItemDTO(1L, "Produto", 2,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(9));

        OrderResponseDTO.ItemDTO item2 = new OrderResponseDTO.ItemDTO(1L, "Produto", 2,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(99)); // só precoPago difere

        assertNotEquals(item1, item2);
    }



    @Test
    void testGettersAndSetters() {
        OrderResponseDTO dto = new OrderResponseDTO();

        OrderResponseDTO.ItemDTO item = new OrderResponseDTO.ItemDTO();
        item.setProdutoId(10L);
        item.setNomeProduto("Product X");
        item.setQuantidade(3);
        item.setPrecoUnitario(BigDecimal.valueOf(29.99));
        item.setDiscountPrice(BigDecimal.valueOf(25.00));
        item.setPrecoPago(BigDecimal.valueOf(89.97));

        List<OrderResponseDTO.ItemDTO> itens = new ArrayList<>();
        itens.add(item);

        dto.setId(1L);
        dto.setClienteId(100L);
        dto.setTotal(BigDecimal.valueOf(89.97));
        dto.setItens(itens);

        assertEquals(1L, dto.getId());
        assertEquals(100L, dto.getClienteId());
        assertEquals(0, dto.getTotal().compareTo(BigDecimal.valueOf(89.97)));
        assertNotNull(dto.getItens());
        assertEquals(1, dto.getItens().size());

        OrderResponseDTO.ItemDTO retrieved = dto.getItens().get(0);
        assertEquals(10L, retrieved.getProdutoId());
        assertEquals("Product X", retrieved.getNomeProduto());
        assertEquals(3, retrieved.getQuantidade());
        assertEquals(0, retrieved.getPrecoUnitario().compareTo(BigDecimal.valueOf(29.99)));
        assertEquals(0, retrieved.getDiscountPrice().compareTo(BigDecimal.valueOf(25.00)));
        assertEquals(0, retrieved.getPrecoPago().compareTo(BigDecimal.valueOf(89.97)));
    }

    @Test
    void testEqualsAndHashCode() {
        OrderResponseDTO.ItemDTO item1 = new OrderResponseDTO.ItemDTO(1L, "Product A", 2,
                BigDecimal.valueOf(15.50), BigDecimal.valueOf(14.50), BigDecimal.valueOf(31.00));

        OrderResponseDTO dto1 = new OrderResponseDTO(1L, 50L, BigDecimal.valueOf(31.00), List.of(item1));
        OrderResponseDTO dto2 = new OrderResponseDTO(1L, 50L, BigDecimal.valueOf(31.00), List.of(item1));

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertTrue(dto1.toString().contains("clienteId"));
        assertTrue(dto1.toString().contains("itens"));
    }

    @Test
    void testCanEqual_withNull() {
        OrderResponseDTO.ItemDTO item = new OrderResponseDTO.ItemDTO();
        assertFalse(item.canEqual(null)); // cobre branch restante
    }

    @Test
    void testEquals_withNullFieldDifference() {
        OrderResponseDTO.ItemDTO item1 = new OrderResponseDTO.ItemDTO(1L, null, 2,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(9));

        OrderResponseDTO.ItemDTO item2 = new OrderResponseDTO.ItemDTO(1L, "Produto", 2,
                BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(9));

        assertNotEquals(item1, item2); // força branch quando nomeProduto é null em um e não no outro
    }

    @Test
    void testToString() {
        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setId(1L);
        dto.setClienteId(100L);
        dto.setTotal(BigDecimal.valueOf(89.97));

        OrderResponseDTO.ItemDTO item = new OrderResponseDTO.ItemDTO();
        item.setProdutoId(10L);
        item.setNomeProduto("Product X");
        item.setQuantidade(3);
        item.setPrecoUnitario(BigDecimal.valueOf(29.99));
        item.setDiscountPrice(BigDecimal.valueOf(25.00));
        item.setPrecoPago(BigDecimal.valueOf(89.97));

        dto.setItens(List.of(item));

        String s = dto.toString();

        assertNotNull(s);
        assertTrue(s.contains("clienteId=100"));
        assertTrue(s.contains("produtoId=10"));
        assertTrue(s.contains("nomeProduto=Product X"));
        assertTrue(s.contains("quantidade=3"));
        assertTrue(s.contains("precoUnitario=29.99"));
        assertTrue(s.contains("discountPrice=25.00") || s.contains("discountPrice=25.0"));
        assertTrue(s.contains("precoPago=89.97"));
    }{
        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setId(1L);
        dto.setClienteId(100L);
        dto.setTotal(BigDecimal.valueOf(89.97));

        OrderResponseDTO.ItemDTO item = new OrderResponseDTO.ItemDTO();
        item.setProdutoId(10L);
        item.setNomeProduto("Product X");
        item.setQuantidade(3);
        item.setPrecoUnitario(BigDecimal.valueOf(29.99));
        item.setDiscountPrice(BigDecimal.valueOf(25.00));
        item.setPrecoPago(BigDecimal.valueOf(89.97));

        List<OrderResponseDTO.ItemDTO> itens = new ArrayList<>();
        itens.add(item);
        dto.setItens(itens);

        String toStringResult = dto.toString();

        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("clienteId=100"));
        assertTrue(toStringResult.contains("itens=[OrderResponseDTO.ItemDTO"));
        assertTrue(toStringResult.contains("produtoId=10"));
        assertTrue(toStringResult.contains("nomeProduto=Product X"));
        assertTrue(toStringResult.contains("quantidade=3"));
        assertTrue(toStringResult.contains("precoUnitario=29.99"));
        assertTrue(toStringResult.contains("discountPrice=25.00") || toStringResult.contains("discountPrice=25.0"));
        assertTrue(toStringResult.contains("precoPago=89.97"));
        // aceita as duas possíveis formatacoes em BigDecimal
        assertTrue(toStringResult.contains("discountPrice=25.00") || toStringResult.contains("discountPrice=25.0"));
        assertTrue(toStringResult.contains("precoPago=89.97"));
    }
}
