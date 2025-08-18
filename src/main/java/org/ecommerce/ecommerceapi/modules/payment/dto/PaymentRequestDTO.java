package org.ecommerce.ecommerceapi.modules.payment.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {
    private Long pedidoId;
    private BigDecimal price;

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal valor) {
        this.price = price;
    }
}


