package org.ecommerce.ecommerceapi.modules.payment.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRequestDTOTest {

    @Test
    void testGettersAndSetters() {
        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setPedidoId(1L);
        dto.setPrice(BigDecimal.TEN);

        assertEquals(1L, dto.getPedidoId());
        assertEquals(BigDecimal.TEN, dto.getPrice());
    }
}
