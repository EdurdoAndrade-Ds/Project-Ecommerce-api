package org.ecommerce.ecommerceapi.modules.payment.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PaymentResponseDTOTest {

    @Test
    void equals_allNulls_areEqual(){
        PaymentResponseDTO a = new PaymentResponseDTO();
        PaymentResponseDTO b = new PaymentResponseDTO();
        assertEquals(a, b);
    }

    private PaymentResponseDTO make(Long id, Long pedidoId, BigDecimal valor,
                                    LocalDateTime data, Long clienteId) {
        PaymentResponseDTO p = new PaymentResponseDTO();
        p.setId(id);
        p.setPedidoId(pedidoId);
        p.setValor(valor);
        p.setDataPagamento(data);
        p.setClienteId(clienteId);
        return p;
    }


    @Test
    void equals_semeRefence_true() {
        PaymentResponseDTO a = new PaymentResponseDTO();

        assertEquals(a, a);
    }

    @Test
    void equals_bothNullPerField_stillEqual() {
        LocalDateTime now = LocalDateTime.now();

        assertEquals(
                make(null, 10L, new BigDecimal("100.50"), now, 5L),
                make(null, 10L, new BigDecimal("100.50"), now, 5L)
        );

        assertEquals(
                make(1L, null, new BigDecimal("100.50"), now, 5L),
                make(1L, null, new BigDecimal("100.50"), now, 5L)
        );

        assertEquals(
                make(1L, 10L, null, now, 5L),
                make(1L, 10L, null, now, 5L)
        );

        assertEquals(
                make(1L, 10L, new BigDecimal("100.50"), null, 5L),
                make(1L, 10L, new BigDecimal("100.50"), null, 5L)
        );


        assertEquals(
                make(1L, 10L, new BigDecimal("100.50"), now, null),
                make(1L, 10L, new BigDecimal("100.50"), now, null)
        );
    }

    @Test
    void equals_nullVsNonNull_perField_notEqual() {
        LocalDateTime now = LocalDateTime.now();

        assertNotEquals(
                make(null, 10L, new BigDecimal("100.50"), now, 5L),
                make(1L, 10L, new BigDecimal("100.50"), now, 5L)
        );

        assertNotEquals(
                make(1L, null, new BigDecimal("100.50"), now, 5L),
                make(1L, 10L, new BigDecimal("100.50"), now, 5L)
        );

        assertNotEquals(
                make(1L, 10L, null, now, 5L),
                make(1L, 10L, new BigDecimal("100.50"), now, 5L)
        );

        assertNotEquals(
                make(1L, 10L, new BigDecimal("100.50"), null, 5L),
                make(1L, 10L, new BigDecimal("100.50"), now, 5L)
        );

        assertNotEquals(
                make(1L, 10L, new BigDecimal("100.50"), now, null),
                make(1L, 10L, new BigDecimal("100.50"), now, 5L)
        );

    }

    @Test
    void equals_differsOnlyOnEachField() {
        LocalDateTime now = LocalDateTime.now();

        PaymentResponseDTO base = make(1L, 10L, new BigDecimal("100.50"), now, 5L);

        assertNotEquals(base, make(2L, 10L, new BigDecimal("100.50"), now, 5L));

        assertNotEquals(base, make(1L, 11L, new BigDecimal("100.50"), now, 5L));

        assertNotEquals(base, make(1L, 10L, new BigDecimal("100.5"), now, 5L));

        assertNotEquals(base, make(1L, 10L, new BigDecimal("100.50"), now.plusSeconds(1), 5L));

        assertNotEquals(base, make(1L, 10L, new BigDecimal("100.50"), now, 6L));
    }

    @Test
    void equals_nullVsNonNull_perField() {
        LocalDateTime now = LocalDateTime.now();

        assertNotEquals(make(1L, 10L, null, now, 5L),
                make(1L, 10L, new BigDecimal("100.50"), now, 5L));
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();

        PaymentResponseDTO p1 = new PaymentResponseDTO();
        p1.setId(1L);
        p1.setPedidoId(10L);
        p1.setValor(BigDecimal.valueOf(100.50));
        p1.setDataPagamento(now);
        p1.setClienteId(5L);

        PaymentResponseDTO p2 = new PaymentResponseDTO();
        p2.setId(1L);
        p2.setPedidoId(10L);
        p2.setValor(BigDecimal.valueOf(100.50));
        p2.setDataPagamento(now);
        p2.setClienteId(5L);

        PaymentResponseDTO p3 = new PaymentResponseDTO();
        p3.setId(2L);
        p3.setPedidoId(20L);
        p3.setValor(BigDecimal.valueOf(200.00));
        p3.setDataPagamento(now.plusDays(1));
        p3.setClienteId(6L);

        // Mesmo conteúdo → equals true
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());

        // Conteúdo diferente → equals false
        assertNotEquals(p1, p3);

        // Comparar com null → equals false
        assertNotEquals(p1, null);

        // Comparar com tipo diferente → equals false
        assertNotEquals(p1, "string");

        assertEquals(1L, p1.getId());
        assertEquals(10L, p1.getPedidoId());
        assertEquals(BigDecimal.valueOf(100.50), p1.getValor());
        assertEquals(now, p1.getDataPagamento());
        assertEquals(5L, p1.getClienteId());


        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1, null);
        assertNotEquals(p1, "string");

        assertNotNull(p1.toString());
        assertTrue(p1.toString().contains("PaymentResponseDTO"));

        assertTrue(p1.canEqual(p2));
        assertFalse(p1.canEqual("string"));
    }

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
