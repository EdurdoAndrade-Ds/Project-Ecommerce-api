package org.ecommerce.ecommerceapi.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePedidoDTO {

    private Long pedidoId;
    private Long customerId;
    private List<Long> productIds;
    private String status;
    private LocalDateTime createdA;
}
