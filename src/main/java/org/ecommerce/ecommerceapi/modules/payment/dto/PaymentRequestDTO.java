package org.ecommerce.ecommerceapi.modules.payment.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@ToString
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {
    private Long pedidoId;
    private BigDecimal price;
}


