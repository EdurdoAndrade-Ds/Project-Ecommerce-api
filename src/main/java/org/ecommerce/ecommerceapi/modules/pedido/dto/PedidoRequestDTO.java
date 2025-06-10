package org.ecommerce.ecommerceapi.modules.pedido.dto;

import lombok.Data;
import java.util.List;

@Data
public class PedidoRequestDTO {
    private List<ItemDTO> itens;

    @Data
    public static class ItemDTO {
        private Long produtoId;
        private Integer quantidade;
    }
}