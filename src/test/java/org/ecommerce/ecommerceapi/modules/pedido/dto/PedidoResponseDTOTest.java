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
        item.setPrecoUnitario(BigDecimal.valueOf(29.99)); // Valor fracionado

        List<PedidoResponseDTO.ItemDTO> itens = new ArrayList<>();
        itens.add(item);

        dto.setId(1L);
        dto.setClienteId(100L);
        dto.setTotal(BigDecimal.valueOf(89.97)); // Valor fracionado
        dto.setItens(itens);

        assertEquals(1L, dto.getId());
        assertEquals(100L, dto.getClienteId());
        assertTrue(dto.getTotal().compareTo(BigDecimal.valueOf(89.97)) == 0); // Comparação com compareTo
        assertNotNull(dto.getItens());
        assertEquals(1, dto.getItens().size());

        PedidoResponseDTO.ItemDTO retrievedItem = dto.getItens().get(0);
        assertEquals(10L, retrievedItem.getProdutoId());
        assertEquals("Produto X", retrievedItem.getNomeProduto());
        assertEquals(3, retrievedItem.getQuantidade());
        assertTrue(retrievedItem.getPrecoUnitario().compareTo(BigDecimal.valueOf(29.99)) == 0); // Comparação com compareTo
    }

    @Test
    void testEqualsAndCanEqual() {
        PedidoResponseDTO dto1 = new PedidoResponseDTO();
        PedidoResponseDTO dto2 = new PedidoResponseDTO();

        // Configurando dto1
        dto1.setId(1L);
        dto1.setClienteId(50L);
        dto1.setTotal(BigDecimal.valueOf(31.00)); // Valor fracionado
        PedidoResponseDTO.ItemDTO item1 = new PedidoResponseDTO.ItemDTO();
        item1.setProdutoId(1L);
        item1.setNomeProduto("Produto A");
        item1.setQuantidade(2);
        item1.setPrecoUnitario(BigDecimal.valueOf(15.50));
        dto1.setItens(List.of(item1));

        // Configurando dto2 com os mesmos valores
        dto2.setId(1L);
        dto2.setClienteId(50L);
        dto2.setTotal(BigDecimal.valueOf(31.00)); // Valor fracionado
        PedidoResponseDTO.ItemDTO item2 = new PedidoResponseDTO.ItemDTO();
        item2.setProdutoId(1L);
        item2.setNomeProduto("Produto A");
        item2.setQuantidade(2);
        item2.setPrecoUnitario(BigDecimal.valueOf(15.50));
        dto2.setItens(List.of(item2));

        // Testando equals
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertTrue(dto1.canEqual(dto2));
        assertFalse(dto1.canEqual(new Object())); // Testando canEqual com objeto de outra classe

        // Alterando um campo em dto2 para torná-lo diferente
        dto2.setClienteId(51L);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCode() {
        PedidoResponseDTO dto1 = new PedidoResponseDTO();
        PedidoResponseDTO dto2 = new PedidoResponseDTO();
        // Configure dto1 e dto2 com dados equivalentes
        dto1.setId(1L);
        dto1.setClienteId(50L);
        dto1.setTotal(BigDecimal.valueOf(31.00)); // Valor fracionado
        dto2.setId(1L);
        dto2.setClienteId(50L);
        dto2.setTotal(BigDecimal.valueOf(31.00)); // Valor fracionado
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
