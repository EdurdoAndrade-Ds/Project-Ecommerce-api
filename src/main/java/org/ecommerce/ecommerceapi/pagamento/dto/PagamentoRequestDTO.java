package org.ecommerce.ecommerceapi.pagamento.dto;

import lombok.Data;

@Data
public class PagamentoRequestDTO {
    private Long pedidoId;
    private String metodo;
}
