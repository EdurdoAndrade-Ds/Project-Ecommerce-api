package org.ecommerce.ecommerceapi.product.dto;

import org.ecommerce.ecommerceapi.product.model.Product;

public class ProductMapperDTO {

    // Converte DTO de entrada (request) para entidade
    public static Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        return product;
    }

    // Converte entidade para DTO de saída (response)
    public static ProductResponseDTO toDTO(Product product) {
        return ProductResponseDTO.fromEntity(product);
    }
}
