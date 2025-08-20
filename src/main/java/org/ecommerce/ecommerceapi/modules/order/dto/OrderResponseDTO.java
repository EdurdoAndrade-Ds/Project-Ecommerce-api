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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemDTO itemDTO = (ItemDTO) o;
            return Objects.equals(produtoId, itemDTO.produtoId)
                    && Objects.equals(nomeProduto, itemDTO.nomeProduto)
                    && Objects.equals(quantidade, itemDTO.quantidade)
                    && Objects.equals(precoUnitario, itemDTO.precoUnitario)
                    && Objects.equals(discountPrice, itemDTO.discountPrice)
                    && Objects.equals(precoPago, itemDTO.precoPago)
                    && itemDTO.canEqual(this);
        }

        @Override
        public int hashCode() {
            return Objects.hash(produtoId, nomeProduto, quantidade, precoUnitario, discountPrice, precoPago);
        }

        public boolean canEqual(Object other) {
            return other instanceof ItemDTO && other.getClass() == ItemDTO.class;
        }
    }



}