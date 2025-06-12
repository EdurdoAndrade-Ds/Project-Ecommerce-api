package org.ecommerce.ecommerceapi.modules.pedido.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PedidoResponseDTOTest {

    @Test
    void testGettersAndSetters() {
        PedidoResponseDTO dto = new PedidoResponseDTO();

        PedidoResponseDTO.ItemDTO item = new PedidoResponseDTO.ItemDTO();
        item.setProdutoId(10L);
        item.setNomeProduto("Produto X");
        item.setQuantidade(3);
        item.setPrecoUnitario(BigDecimal.valueOf(29.99));

        List<PedidoResponseDTO.ItemDTO> itens = new ArrayList<>();
        itens.add(item);

        dto.setId(1L);
        dto.setClienteId(100L);
        dto.setTotal(BigDecimal.valueOf(89.97));
        dto.setItens(itens);

        assertEquals(1L, dto.getId());
        assertEquals(100L, dto.getClienteId());
        assertEquals(BigDecimal.valueOf(89.97), dto.getTotal());
        assertNotNull(dto.getItens());
        assertEquals(1, dto.getItens().size());

        PedidoResponseDTO.ItemDTO retrievedItem = dto.getItens().get(0);
        assertEquals(10L, retrievedItem.getProdutoId());
        assertEquals("Produto X", retrievedItem.getNomeProduto());
        assertEquals(3, retrievedItem.getQuantidade());
        assertEquals(BigDecimal.valueOf(29.99), retrievedItem.getPrecoUnitario());
    }

    @Test
    void testEqualsHashCodeToString() {
        PedidoResponseDTO.ItemDTO item1 = new PedidoResponseDTO.ItemDTO();
        item1.setProdutoId(1L);
        item1.setNomeProduto("Produto A");
        item1.setQuantidade(2);
        item1.setPrecoUnitario(BigDecimal.valueOf(15.50));

        PedidoResponseDTO.ItemDTO item2 = new PedidoResponseDTO.ItemDTO();
        item2.setProdutoId(1L);
        item2.setNomeProduto("Produto A");
        item2.setQuantidade(2);
        item2.setPrecoUnitario(BigDecimal.valueOf(15.50));

        PedidoResponseDTO.ItemDTO item3 = new PedidoResponseDTO.ItemDTO();
        item3.setProdutoId(2L);
        item3.setNomeProduto("Produto B");
        item3.setQuantidade(1);
        item3.setPrecoUnitario(BigDecimal.valueOf(20.00));

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
        assertNotEquals(item1, item3);
        assertNotEquals(item1, null);
        assertNotEquals(item1, new Object());

        assertTrue(item1.toString().contains("produtoId=1"));
        assertTrue(item1.toString().contains("nomeProduto=Produto A"));

        assertTrue(item1.canEqual(item2));
        assertFalse(item1.canEqual("qualquer coisa"));

        PedidoResponseDTO dto1 = new PedidoResponseDTO();
        PedidoResponseDTO dto2 = new PedidoResponseDTO();

        dto1.setId(1L);
        dto1.setClienteId(50L);
        dto1.setTotal(BigDecimal.valueOf(31.00));
        dto1.setItens(List.of(item1));

        dto2.setId(1L);
        dto2.setClienteId(50L);
        dto2.setTotal(BigDecimal.valueOf(31.00));
        dto2.setItens(List.of(item2));

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertTrue(dto1.toString().contains("itens"));
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, new Object());
        assertTrue(dto1.canEqual(dto2));
        assertFalse(dto1.canEqual("obj"));
    }
}
