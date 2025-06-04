package org.ecommerce.ecommerceapi.product.dto;

import org.ecommerce.ecommerceapi.product.model.Product;
import org.ecommerce.ecommerceapi.inventory.model.Inventory;

public class ProductMapperDTO {

    // Converte DTO de entrada (request) para entidade
    public static Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        // Criar e associar o estoque (Inventory)
        Inventory inventory = new Inventory();
        inventory.setQuantity(dto.getQuantidadeEstoque() != null ? dto.getQuantidadeEstoque() : 0);
        inventory.setProduct(product);

        product.setInventory(inventory);

        return product;
    }

    // Converte entidade para DTO de saída (response)
    public static ProductResponseDTO toDTO(Product product) {
        return ProductResponseDTO.fromEntity(product);
    }
}
