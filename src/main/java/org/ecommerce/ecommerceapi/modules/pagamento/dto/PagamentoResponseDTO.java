package org.ecommerce.ecommerceapi.modules.pagamento.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoResponseDTO {
    private Long id;
    private Long pedidoId;
    private BigDecimal valor;
    private String status;
    private LocalDateTime data;
}