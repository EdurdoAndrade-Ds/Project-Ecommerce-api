package org.ecommerce.ecommerceapi.inventory.dto;

import lombok.Data;

@Data
public class UpdateStockDTO {

    private Long productId;
    private Integer quantityChange; // Corrigido para refletir alteração de estoque

}
