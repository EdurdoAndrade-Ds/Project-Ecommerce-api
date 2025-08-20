package org.ecommerce.ecommerceapi.modules.order.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseDTOTest {

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
