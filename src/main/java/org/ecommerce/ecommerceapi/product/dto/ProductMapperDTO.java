package org.ecommerce.ecommerceapi.product.dto;

import org.ecommerce.ecommerceapi.inventory.model.Inventory;
import org.ecommerce.ecommerceapi.product.model.Product;

public class ProductMapperDTO {

    public static Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        // Cria e associa o inventário ao produto
        Inventory inventory = new Inventory();
        inventory.setQuantity(dto.getStockQuantity());
        inventory.setProduct(product); // Importante manter a associação bidirecional
        product.setInventory(inventory); // Isso já cuida do setProduct internamente, mas mantém por segurança

        return product;
    }

    public static ProductResponseDTO toDTO(Product product) {
        return ProductResponseDTO.fromEntity(product);
    }
}
