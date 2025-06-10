package org.ecommerce.ecommerceapi.modules.pagamento.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PagamentoRequestDTO {
    private Long pedidoId;
    private BigDecimal valor;
}
