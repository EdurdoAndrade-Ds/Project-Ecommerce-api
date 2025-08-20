package org.ecommerce.ecommerceapi.modules.order.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;                // ID do pedido
    private Long clienteId;         // ID do cliente que fez o pedido
    private BigDecimal total;        // Total do pedido
    private List<ItemDTO> itens;    // Lista de itens no pedido


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDTO {
        private Long produtoId;
        private String nomeProduto;
        private Integer quantidade;
        private BigDecimal precoUnitario;
        private BigDecimal discountPrice;
        private BigDecimal precoPago;
    }



}