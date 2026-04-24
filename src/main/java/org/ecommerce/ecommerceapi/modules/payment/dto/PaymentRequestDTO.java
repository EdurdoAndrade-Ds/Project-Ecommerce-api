package org.ecommerce.ecommerceapi.modules.payment.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {

    @NotNull(message = "ID do pedido é obrigatório")
    private Long pedidoId;

    @NotNull(message = "Valor do pagamento é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    private BigDecimal price;
}
