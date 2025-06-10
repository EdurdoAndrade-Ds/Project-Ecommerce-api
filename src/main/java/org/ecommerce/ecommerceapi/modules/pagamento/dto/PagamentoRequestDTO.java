package org.ecommerce.ecommerceapi.modules.pagamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoRequestDTO {
    private Long pedidoId;
    private BigDecimal valor;
    private String metodo;
}
