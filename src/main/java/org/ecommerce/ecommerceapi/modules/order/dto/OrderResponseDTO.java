package org.ecommerce.ecommerceapi.modules.order.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @Builder
@Data
public class OrderResponseDTO {
    private Long id;                // ID do pedido
    private Long clienteId;         // ID do cliente que fez o pedido
    private BigDecimal total;        // Total do pedido
    private List<ItemDTO> itens;    // Lista de itens no pedido

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderResponseDTO)) return false;
        OrderResponseDTO that = (OrderResponseDTO) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(clienteId, that.clienteId) &&
               Objects.equals(total, that.total) &&
               Objects.equals(itens, that.itens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clienteId, total, itens);
    }

    @Data
    public static class ItemDTO {
        private Long produtoId;      // ID do produto
        private String nomeProduto;   // Nome do produto
        private Integer quantidade;   // Quantidade do produto no pedido
        private BigDecimal precoUnitario; // Preço unitário do produto
        private BigDecimal discountPrice; // Preço com desconto do produto, se aplicável
        private BigDecimal precoPago; // Preço total pago pelo produto

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ItemDTO)) return false;
            ItemDTO itemDTO = (ItemDTO) o;
            return Objects.equals(produtoId, itemDTO.produtoId) &&
                   Objects.equals(nomeProduto, itemDTO.nomeProduto) &&
                   Objects.equals(quantidade, itemDTO.quantidade) &&
                   Objects.equals(precoUnitario, itemDTO.precoUnitario) &&
                   Objects.equals(discountPrice, itemDTO.discountPrice) &&
                   Objects.equals(precoPago, itemDTO.precoPago);
        }

        @Override
        public int hashCode() {
            return Objects.hash(produtoId, nomeProduto, quantidade, precoUnitario, discountPrice, precoPago);
        }
    }
}