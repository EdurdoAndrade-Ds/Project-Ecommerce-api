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

    @Test
    void testEqualsAndHashCode_sameValues() {
        PaymentRequestDTO dto1 = PaymentRequestDTO.builder()
                .pedidoId(1L)
                .price(BigDecimal.valueOf(100))
                .build();

        PaymentRequestDTO dto2 = PaymentRequestDTO.builder()
                .pedidoId(1L)
                .price(BigDecimal.valueOf(100))
                .build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testEquals_differentValues_shouldNotBeEqual() {
        PaymentRequestDTO dto1 = new PaymentRequestDTO(1L, BigDecimal.valueOf(100));
        PaymentRequestDTO dto2 = new PaymentRequestDTO(2L, BigDecimal.valueOf(200));

        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_withNullAndDifferentClass() {
        PaymentRequestDTO dto = new PaymentRequestDTO(1L, BigDecimal.valueOf(100));

        assertNotEquals(dto, null);
        assertNotEquals(dto, "string qualquer");
    }

    @Test
    void testEquals_sameReference_shouldBeTrue() {
        PaymentRequestDTO dto = new PaymentRequestDTO(1L, BigDecimal.valueOf(100));
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_withNullFieldDifference() {
        PaymentRequestDTO dto1 = new PaymentRequestDTO(null, BigDecimal.valueOf(100));
        PaymentRequestDTO dto2 = new PaymentRequestDTO(1L, BigDecimal.valueOf(100));

        assertNotEquals(dto1, dto2);
    }

    @Test
    void testBuilder_createsObjectCorrectly() {
        PaymentRequestDTO dto = PaymentRequestDTO.builder()
                .pedidoId(10L)
                .price(BigDecimal.valueOf(250))
                .build();

        assertEquals(10L, dto.getPedidoId());
        assertEquals(BigDecimal.valueOf(250), dto.getPrice());
    }

    @Test
    void testToString_containsFields() {
        PaymentRequestDTO dto = new PaymentRequestDTO(5L, BigDecimal.valueOf(50));

        String toString = dto.toString();
        assertTrue(toString.contains("5"));
        assertTrue(toString.contains("50"));
    }

    @Test
    void testEquals_withNullPedidoIdDifference() {
        PaymentRequestDTO dto1 = new PaymentRequestDTO(null, BigDecimal.valueOf(100));
        PaymentRequestDTO dto2 = new PaymentRequestDTO(1L, BigDecimal.valueOf(100));

        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_withNullPriceDifference() {
        PaymentRequestDTO dto1 = new PaymentRequestDTO(1L, null);
        PaymentRequestDTO dto2 = new PaymentRequestDTO(1L, BigDecimal.valueOf(200));

        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_canEqualFalseForSubclass() {
        class FakePaymentRequestDTO extends PaymentRequestDTO {
            public FakePaymentRequestDTO(Long id, BigDecimal price) {
                super(id, price);
            }
        }

        PaymentRequestDTO dto = new PaymentRequestDTO(1L, BigDecimal.valueOf(100));
        FakePaymentRequestDTO fake = new FakePaymentRequestDTO(1L, BigDecimal.valueOf(100));

        assertNotEquals(dto, fake); // força ramo do canEqual
    }

    @Test
    void testCanEqual_shouldReturnFalseForDifferentClass() {
        PaymentRequestDTO dto = new PaymentRequestDTO(1L, BigDecimal.valueOf(100));
        assertFalse(dto.canEqual("string"));
    }
}
