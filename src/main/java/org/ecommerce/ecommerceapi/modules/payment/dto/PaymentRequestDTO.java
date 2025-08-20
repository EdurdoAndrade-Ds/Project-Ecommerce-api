package org.ecommerce.ecommerceapi.modules.payment.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {
    private Long pedidoId;
    private BigDecimal price;

    public boolean canEqual(Object other) {
        return other instanceof PaymentRequestDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentRequestDTO that = (PaymentRequestDTO) o;
        if (!that.canEqual(this)) return false;
        return Objects.equals(pedidoId, that.pedidoId) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedidoId, price);
    }
}
