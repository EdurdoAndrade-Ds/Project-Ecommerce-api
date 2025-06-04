package org.ecommerce.ecommerceapi.product.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity; // agora representa o estoque dentro do Inventory
}
