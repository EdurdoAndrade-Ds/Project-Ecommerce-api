package org.ecommerce.ecommerceapi.modules.pagamento.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PagamentoResponseDTO {
    private Long id;
    private BigDecimal valor;
    private String status;
    private Long pedidoId;
    private LocalDateTime data;
}