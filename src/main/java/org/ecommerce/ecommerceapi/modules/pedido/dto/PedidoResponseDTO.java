package org.ecommerce.ecommerceapi.modules.pedido.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PedidoResponseDTO {
    private Long id;
    private Long clienteId;
    private BigDecimal total;
    private List<ItemDTO> itens;

    @Data
    public static class ItemDTO {
        private Long produtoId;
        private String nomeProduto;
        private Integer quantidade;
        private BigDecimal precoUnitario;

    }

}