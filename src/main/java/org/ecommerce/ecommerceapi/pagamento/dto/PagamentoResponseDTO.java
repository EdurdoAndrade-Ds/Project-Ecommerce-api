package org.ecommerce.ecommerceapi.pagamento.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PagamentoResponseDTO {
    private Long id;
    private Long pedidoId;
    private String metodo;
    private String status;
    private LocalDateTime dataPagamento;
}
