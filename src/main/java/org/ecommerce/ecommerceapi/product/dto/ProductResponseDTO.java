package org.ecommerce.ecommerceapi.product.dto;

import lombok.Builder;
import lombok.Data;
import org.ecommerce.ecommerceapi.product.model.Product;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantidadeEstoque;

    
    public static ProductResponseDTO fromEntity(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantidadeEstoque(product.getQuantidadeEstoque())
                .build();
    }
}
