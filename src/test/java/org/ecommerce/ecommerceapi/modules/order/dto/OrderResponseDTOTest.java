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
        item.setNomeProduto("Produto X");
        item.setQuantidade(3);
        item.setPrecoUnitario(BigDecimal.valueOf(29.99)); // Valor fracionado
        item.setDiscountPrice(BigDecimal.valueOf(25.00));
        item.setPrecoPago(BigDecimal.valueOf(89.97));

        List<OrderResponseDTO.ItemDTO> itens = new ArrayList<>();
        itens.add(item);

        dto.setId(1L);
        dto.setClienteId(100L);
        dto.setTotal(BigDecimal.valueOf(89.97)); // Valor fracionado
        dto.setItens(itens);

        assertEquals(1L, dto.getId());
        assertEquals(100L, dto.getClienteId());
        assertTrue(dto.getTotal().compareTo(BigDecimal.valueOf(89.97)) == 0);
        assertNotNull(dto.getItens());
        assertEquals(1, dto.getItens().size());

        OrderResponseDTO.ItemDTO retrievedItem = dto.getItens().get(0);
        assertEquals(10L, retrievedItem.getProdutoId());
        assertEquals("Produto X", retrievedItem.getNomeProduto());
        assertEquals(3, retrievedItem.getQuantidade());
        assertTrue(retrievedItem.getPrecoUnitario().compareTo(BigDecimal.valueOf(29.99)) == 0);
        assertTrue(retrievedItem.getDiscountPrice().compareTo(BigDecimal.valueOf(25.00)) == 0);
        assertTrue(retrievedItem.getPrecoPago().compareTo(BigDecimal.valueOf(89.97)) == 0);
    }

    @Test
    void testEqualsAndHashCode() {
        OrderResponseDTO dto1 = new OrderResponseDTO();
        OrderResponseDTO dto2 = new OrderResponseDTO();

        OrderResponseDTO.ItemDTO item1 = new OrderResponseDTO.ItemDTO();
        item1.setProdutoId(1L);
        item1.setNomeProduto("Produto A");
        item1.setQuantidade(2);
        item1.setPrecoUnitario(BigDecimal.valueOf(15.50));
        item1.setDiscountPrice(BigDecimal.valueOf(14.50));
        item1.setPrecoPago(BigDecimal.valueOf(31.00));

        dto1.setId(1L);
        dto1.setClienteId(50L);
        dto1.setTotal(BigDecimal.valueOf(31.00));
        dto1.setItens(List.of(item1));

        OrderResponseDTO.ItemDTO item2 = new OrderResponseDTO.ItemDTO();
        item2.setProdutoId(1L);
        item2.setNomeProduto("Produto A");
        item2.setQuantidade(2);
        item2.setPrecoUnitario(BigDecimal.valueOf(15.50));
        item2.setDiscountPrice(BigDecimal.valueOf(14.50));
        item2.setPrecoPago(BigDecimal.valueOf(31.00));

        dto2.setId(1L);
        dto2.setClienteId(50L);
        dto2.setTotal(BigDecimal.valueOf(31.00));
        dto2.setItens(List.of(item2));

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
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
        item.setNomeProduto("Produto X");
        item.setQuantidade(3);
        item.setPrecoUnitario(BigDecimal.valueOf(29.99));
        item.setDiscountPrice(BigDecimal.valueOf(25.00));
        item.setPrecoPago(BigDecimal.valueOf(89.97));

        List<OrderResponseDTO.ItemDTO> itens = new ArrayList<>();
        itens.add(item);
        dto.setItens(itens);

        String toStringResult = dto.toString();

        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("clienteId=100"));
        assertTrue(toStringResult.contains("total=89.97"));
        assertTrue(toStringResult.contains("itens=[PedidoResponseDTO.ItemDTO"));
        assertTrue(toStringResult.contains("produtoId=10"));
        assertTrue(toStringResult.contains("nomeProduto=Produto X"));
        assertTrue(toStringResult.contains("quantidade=3"));
        assertTrue(toStringResult.contains("precoUnitario=29.99"));
        // aceita as duas possíveis formatacoes em BigDecimal
        assertTrue(toStringResult.contains("discountPrice=25.00") || toStringResult.contains("discountPrice=25.0"));
        assertTrue(toStringResult.contains("precoPago=89.97"));
    }
}
