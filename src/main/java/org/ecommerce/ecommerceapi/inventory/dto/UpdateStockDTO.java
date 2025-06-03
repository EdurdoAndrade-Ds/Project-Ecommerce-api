package org.ecommerce.ecommerceapi.inventory.dto;

import lombok.Data;

@Data
public class UpdateStockDTO {

    private Long productId;
    private Integer quantityChange; // Corrigido para refletir alteração de estoque

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(Integer quantityChange) {
        this.quantityChange = quantityChange;
    }
}
