package org.ecommerce.ecommerceapi.modules.product.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductRequestDTOTest {

    @Test
    void deveCriarProductRequestDTOCorretamente() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setNome("Produto Teste");
        dto.setDescricao("Descrição Teste");
        dto.setPreco(BigDecimal.valueOf(99.99));
        dto.setEstoque(10);

        assertEquals("Produto Teste", dto.getNome());
        assertEquals("Descrição Teste", dto.getDescricao());
        assertEquals(BigDecimal.valueOf(99.99), dto.getPreco());
        assertEquals(10, dto.getEstoque());
    }

    @Test
    void deveCompararDoisDTOsIguais() {
        ProductRequestDTO dto1 = new ProductRequestDTO();
        dto1.setNome("Produto");
        dto1.setDescricao("Descrição");
        dto1.setPreco(BigDecimal.TEN);
        dto1.setEstoque(5);

        ProductRequestDTO dto2 = new ProductRequestDTO();
        dto2.setNome("Produto");
        dto2.setDescricao("Descrição");
        dto2.setPreco(BigDecimal.TEN);
        dto2.setEstoque(5);

        assertEquals(dto1, dto2); // funciona porque @Data gera equals/hashCode
    }
}
