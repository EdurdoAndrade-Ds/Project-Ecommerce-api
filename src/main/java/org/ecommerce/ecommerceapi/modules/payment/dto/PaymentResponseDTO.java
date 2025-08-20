package org.ecommerce.ecommerceapi.modules.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private Long id;
    private Long pedidoId;
    private BigDecimal valor;
    private LocalDateTime dataPagamento;
    private Long clienteId;


}
