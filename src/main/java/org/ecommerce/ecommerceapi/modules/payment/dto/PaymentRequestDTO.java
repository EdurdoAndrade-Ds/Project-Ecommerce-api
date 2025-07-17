package org.ecommerce.ecommerceapi.modules.payment.dto;

import java.math.BigDecimal;

public class PaymentRequestDTO {
    private Long pedidoId;
    private BigDecimal valor;

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}


