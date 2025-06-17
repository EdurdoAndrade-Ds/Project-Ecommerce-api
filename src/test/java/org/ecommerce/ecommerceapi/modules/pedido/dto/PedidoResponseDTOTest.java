package org.ecommerce.ecommerceapi.modules.pedido.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        item.setDescountPrice(BigDecimal.valueOf(25.00));
        item.setPrecoPago(BigDecimal.valueOf(89.97));

        List<PedidoResponseDTO.ItemDTO> itens = new ArrayList<>();
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

        PedidoResponseDTO.ItemDTO retrievedItem = dto.getItens().get(0);
        assertEquals(10L, retrievedItem.getProdutoId());
        assertEquals("Produto X", retrievedItem.getNomeProduto());
        assertEquals(3, retrievedItem.getQuantidade());
        assertTrue(retrievedItem.getPrecoUnitario().compareTo(BigDecimal.valueOf(29.99)) == 0);
        assertTrue(retrievedItem.getDescountPrice().compareTo(BigDecimal.valueOf(25.00)) == 0);
        assertTrue(retrievedItem.getPrecoPago().compareTo(BigDecimal.valueOf(89.97)) == 0);
    }

    @Test
    void testEqualsAndHashCode() {
        PedidoResponseDTO dto1 = new PedidoResponseDTO();
        PedidoResponseDTO dto2 = new PedidoResponseDTO();

        PedidoResponseDTO.ItemDTO item1 = new PedidoResponseDTO.ItemDTO();
        item1.setProdutoId(1L);
        item1.setNomeProduto("Produto A");
        item1.setQuantidade(2);
        item1.setPrecoUnitario(BigDecimal.valueOf(15.50));
        item1.setDescountPrice(BigDecimal.valueOf(14.50));
        item1.setPrecoPago(BigDecimal.valueOf(31.00));

        dto1.setId(1L);
        dto1.setClienteId(50L);
        dto1.setTotal(BigDecimal.valueOf(31.00));
        dto1.setItens(List.of(item1));

        PedidoResponseDTO.ItemDTO item2 = new PedidoResponseDTO.ItemDTO();
        item2.setProdutoId(1L);
        item2.setNomeProduto("Produto A");
        item2.setQuantidade(2);
        item2.setPrecoUnitario(BigDecimal.valueOf(15.50));
        item2.setDescountPrice(BigDecimal.valueOf(14.50));
        item2.setPrecoPago(BigDecimal.valueOf(31.00));

        dto2.setId(1L);
        dto2.setClienteId(50L);
        dto2.setTotal(BigDecimal.valueOf(31.00));
        dto2.setItens(List.of(item2));

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setClienteId(51L);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        PedidoResponseDTO dto = new PedidoResponseDTO();

        dto.setId(1L);
        dto.setClienteId(100L);
        dto.setTotal(BigDecimal.valueOf(89.97));

        PedidoResponseDTO.ItemDTO item = new PedidoResponseDTO.ItemDTO();
        item.setProdutoId(10L);
        item.setNomeProduto("Produto X");
        item.setQuantidade(3);
        item.setPrecoUnitario(BigDecimal.valueOf(29.99));
        item.setDescountPrice(BigDecimal.valueOf(25.00));
        item.setPrecoPago(BigDecimal.valueOf(89.97));

        List<PedidoResponseDTO.ItemDTO> itens = new ArrayList<>();
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
        assertTrue(toStringResult.contains("descountPrice=25.00") || toStringResult.contains("descountPrice=25.0"));
        assertTrue(toStringResult.contains("precoPago=89.97"));
    }
}
