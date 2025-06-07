package org.ecommerce.ecommerceapi.pagamento.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class PagamentoRequestDTO {

    @Schema(description = "ID do pedido relacionado ao pagamento", example = "123")
    private Long pedidoId;

    @Schema(description = "Método de pagamento", example = "CARTAO")
    private String metodo;

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}
