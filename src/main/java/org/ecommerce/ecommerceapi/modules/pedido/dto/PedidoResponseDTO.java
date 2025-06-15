package org.ecommerce.ecommerceapi.modules.pedido.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PedidoResponseDTO {
    private Long id;                // ID do pedido
    private Long clienteId;         // ID do cliente que fez o pedido
    private BigDecimal total;        // Total do pedido
    private List<ItemDTO> itens;    // Lista de itens no pedido

    @Data
    public static class ItemDTO {
        private Long produtoId;      // ID do produto
        private String nomeProduto;   // Nome do produto
        private Integer quantidade;   // Quantidade do produto no pedido
        private BigDecimal precoUnitario; // Preço unitário do produto (pode ser o preço original ou o preço com desconto)
        private BigDecimal descountPrice; // Preço com desconto do produto, se aplicável
        private BigDecimal precoPago; // Preço total pago pelo produto (soma de todos os itens)
    }
}