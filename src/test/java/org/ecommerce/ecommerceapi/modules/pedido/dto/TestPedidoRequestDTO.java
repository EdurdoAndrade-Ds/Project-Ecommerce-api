package org.ecommerce.ecommerceapi.modules.pedido.dto;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TestPedidoRequestDTO {

    @Test
    void testGettersAndSetters() {
        PedidoRequestDTO dto = new PedidoRequestDTO();

        PedidoRequestDTO.ItemDTO item = new PedidoRequestDTO.ItemDTO();
        item.setProdutoId(100L);
        item.setQuantidade(2);

        List<PedidoRequestDTO.ItemDTO> itens = new ArrayList<>();
        itens.add(item);

        dto.setItens(itens);

        assertNotNull(dto.getItens());
        assertEquals(1, dto.getItens().size());

        PedidoRequestDTO.ItemDTO retrievedItem = dto.getItens().get(0);
        assertEquals(100L, retrievedItem.getProdutoId());
        assertEquals(2, retrievedItem.getQuantidade());
    }

    @Test
    void testEqualsHashCodeToString() {
        PedidoRequestDTO.ItemDTO item1 = new PedidoRequestDTO.ItemDTO();
        item1.setProdutoId(1L);
        item1.setQuantidade(5);

        PedidoRequestDTO.ItemDTO item2 = new PedidoRequestDTO.ItemDTO();
        item2.setProdutoId(1L);
        item2.setQuantidade(5);

        PedidoRequestDTO.ItemDTO item3 = new PedidoRequestDTO.ItemDTO();
        item3.setProdutoId(2L);
        item3.setQuantidade(3);

        // equals and hashCode on ItemDTO
        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
        assertNotEquals(item1, item3);

        // toString contains expected info
        assertTrue(item1.toString().contains("produtoId=1"));
        assertTrue(item1.toString().contains("quantidade=5"));

        // Test PedidoRequestDTO equals/hashCode/toString
        PedidoRequestDTO dto1 = new PedidoRequestDTO();
        PedidoRequestDTO dto2 = new PedidoRequestDTO();

        dto1.setItens(List.of(item1));
        dto2.setItens(List.of(item2));

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertTrue(dto1.toString().contains("itens"));
    }
}
