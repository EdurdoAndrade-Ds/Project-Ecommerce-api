package org.ecommerce.ecommerceapi.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ecommerce.ecommerceapi.product.model.Product;
import org.ecommerce.ecommerceapi.inventory.model.Inventory;

import java.math.BigDecimal;

@Data
@Schema(description = "DTO para retorno de informações de um produto")
public class ProductResponseDTO {

    @Schema(description = "ID do produto", example = "1")
    private Long id;

    @Schema(description = "Nome do produto", example = "Notebook Lenovo")
    private String name;

    @Schema(description = "Descrição do produto", example = "Notebook com 16GB RAM e SSD 512GB")
    private String description;

    @Schema(description = "Preço", example = "3799.99")
    private BigDecimal price;

    @Schema(description = "Quantidade em estoque", example = "5")
    private Integer stockQuantity;

    public static ProductResponseDTO fromEntity(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        // Ajuste aqui conforme seu model Product:
        if (product.getInventory() != null) {
            dto.setStockQuantity(product.getInventory().getQuantity());
        } else {
            dto.setStockQuantity(0);
        }
        return dto;
    }
}
