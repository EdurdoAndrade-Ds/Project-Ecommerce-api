package org.ecommerce.ecommerceapi.modules.payment.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PaymentResponseDTOTest {

    @Test
    void testGettersSettersAndToString() {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        LocalDateTime now = LocalDateTime.now();
        dto.setId(1L);
        dto.setPedidoId(2L);
        dto.setValor(BigDecimal.TEN);
        dto.setDataPagamento(now);

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getPedidoId());
        assertEquals(BigDecimal.TEN, dto.getValor());
        assertEquals(now, dto.getDataPagamento());

        String str = dto.toString();
        assertNotNull(str);
        assertTrue(str.contains("pedidoId"));
    }
}
