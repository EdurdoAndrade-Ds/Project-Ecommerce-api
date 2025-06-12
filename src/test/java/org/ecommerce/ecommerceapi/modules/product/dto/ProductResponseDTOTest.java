package org.ecommerce.ecommerceapi.modules.product.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductResponseDTOTest {

    @Test
    void deveCriarEDefinirCamposCorretamente() {
        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(1L);
        dto.setNome("Produto Teste");
        dto.setDescricao("Descrição de Teste");
        dto.setPreco(BigDecimal.valueOf(99.99));
        dto.setEstoque(10);

        assertEquals(1L, dto.getId());
        assertEquals("Produto Teste", dto.getNome());
        assertEquals("Descrição de Teste", dto.getDescricao());
        assertEquals(BigDecimal.valueOf(99.99), dto.getPreco());
        assertEquals(10, dto.getEstoque());
    }
}
