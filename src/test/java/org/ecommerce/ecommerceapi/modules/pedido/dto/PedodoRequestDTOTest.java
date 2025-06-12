package org.ecommerce.ecommerceapi.modules.pedido.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PedidoRequestDTOTest {

    @Test
    void testPedidoRequestDTOGettersSettersAndEquals() {
        PedidoRequestDTO dto1 = new PedidoRequestDTO();

        PedidoRequestDTO.ItemDTO item1 = new PedidoRequestDTO.ItemDTO();
        item1.setProdutoId(1L);
        item1.setQuantidade(5);

        PedidoRequestDTO.ItemDTO item2 = new PedidoRequestDTO.ItemDTO();
        item2.setProdutoId(2L);
        item2.setQuantidade(10);

        dto1.setItens(Arrays.asList(item1, item2));

        // Testa getters
        assertNotNull(dto1.getItens());
        assertEquals(2, dto1.getItens().size());
        assertEquals(Long.valueOf(1), dto1.getItens().get(0).getProdutoId());
        assertEquals(Integer.valueOf(5), dto1.getItens().get(0).getQuantidade());

        // Testa equals e hashCode entre dois objetos iguais
        PedidoRequestDTO dto2 = new PedidoRequestDTO();
        PedidoRequestDTO.ItemDTO item3 = new PedidoRequestDTO.ItemDTO();
        item3.setProdutoId(1L);
        item3.setQuantidade(5);

        PedidoRequestDTO.ItemDTO item4 = new PedidoRequestDTO.ItemDTO();
        item4.setProdutoId(2L);
        item4.setQuantidade(10);

        dto2.setItens(Arrays.asList(item3, item4));

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        PedidoRequestDTO.ItemDTO item1 = new PedidoRequestDTO.ItemDTO();
        item1.setProdutoId(1L);
        item1.setQuantidade(5);
        
        PedidoRequestDTO.ItemDTO item2 = new PedidoRequestDTO.ItemDTO();
        item2.setProdutoId(2L);
        item2.setQuantidade(10);
        
        dto.setItens(Arrays.asList(item1, item2));

        String dtoString = dto.toString();
        assertTrue(dtoString.contains("itens="));
        assertTrue(dtoString.contains("produtoId=1"));
        assertTrue(dtoString.contains("quantidade=5"));
        assertTrue(dtoString.contains("produtoId=2"));
        assertTrue(dtoString.contains("quantidade=10"));
    }

    @Test
    void testItemDTOEqualsNullAndCanEqual() {
        PedidoRequestDTO.ItemDTO item = new PedidoRequestDTO.ItemDTO();

        // Inicialmente, campos nulos
        assertNull(item.getProdutoId());
        assertNull(item.getQuantidade());

        // Testa canEqual
        assertTrue(item.canEqual(new PedidoRequestDTO.ItemDTO()));
        assertFalse(item.canEqual(new Object()));

        // Testa equals com null e objeto diferente
        assertFalse(item.equals(null));
        assertFalse(item.equals(new Object()));
    }

    @Test
    void testPedidoRequestDTOWithNullItems() {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setItens(null);

        assertNull(dto.getItens());
    }

    @Test
    void testItemDTOEqualsDifferentObjects() {
        PedidoRequestDTO.ItemDTO item1 = new PedidoRequestDTO.ItemDTO();
        item1.setProdutoId(1L);
        item1.setQuantidade(5);

        PedidoRequestDTO.ItemDTO item2 = new PedidoRequestDTO.ItemDTO();
        item2.setProdutoId(2L);
        item2.setQuantidade(10);

        assertNotEquals(item1, item2);
    }
}