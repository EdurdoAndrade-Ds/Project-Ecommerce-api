package org.ecommerce.ecommerceapi.modules.payment.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {
    private Long id;
    private Long pedidoId;
    private BigDecimal valor;
    private LocalDateTime dataPagamento;
}
