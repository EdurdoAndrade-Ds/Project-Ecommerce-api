package org.ecommerce.ecommerceapi.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.ecommerce.ecommerceapi.product.model.Product;
import org.ecommerce.ecommerceapi.product.model.Inventory;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "DTO para retorno de informações de um produto")
public class ProductResponseDTO {

    @Schema(description = "ID do produto", example = "1")
    private Long id;

    @Schema(description = "Nome do produto", example = "Notebook Lenovo")
    private String name;

    @Schema(description = "Descrição do produto", example = "Notebook com 16GB RAM e SSD 512GB")
    private String description;

    @Schema(description = "Preço do produto", example = "3799.99")
    private BigDecimal price;

    @Schema(description = "Quantidade atual em estoque", example = "8")
    private Integer estoque;

    public static ProductResponseDTO fromEntity(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .estoque(product.getInventory() != null ? product.getInventory().getQuantity() : null)
                .build();
    }

    public ProductResponseDTO criar(ProductRequestDTO dto) {
        Product product = ProductMapperDTO.toEntity(dto);
        Inventory inventory = new Inventory();
        inventory.setQuantity(dto.getStockQuantity()); // ou dto.getEstoque()
        inventory.setProduct(product);
        product.setInventory(inventory);
        Product salvo = productRepository.save(product);
        return ProductMapperDTO.toDTO(salvo);
    }

    public ProductResponseDTO atualizar(Long id, ProductRequestDTO dto) {
        Product existente = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        existente.setName(dto.getName());
        existente.setDescription(dto.getDescription());
        existente.setPrice(dto.getPrice());

        Inventory inventory = existente.getInventory();
        if (inventory == null) {
            inventory = new Inventory();
            inventory.setProduct(existente);
            existente.setInventory(inventory);
        }
        inventory.setQuantity(dto.getStockQuantity()); // ou dto.getEstoque()

        Product atualizado = productRepository.save(existente);
        return ProductMapperDTO.toDTO(atualizado);
    }
}
