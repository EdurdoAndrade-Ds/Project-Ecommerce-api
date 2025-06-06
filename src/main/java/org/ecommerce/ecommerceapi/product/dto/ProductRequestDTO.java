package org.ecommerce.ecommerceapi.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Schema(description = "DTO para criação ou atualização de um produto")
public class ProductRequestDTO {

    @Schema(description = "Nome do produto", example = "Notebook Lenovo")
    private String name;

    @Schema(description = "Descrição detalhada do produto", example = "Notebook com 16GB RAM e SSD 512GB")
    private String description;

    @Schema(description = "Preço do produto", example = "3799.99")
    private BigDecimal price;

    @Schema(description = "Quantidade inicial em estoque", example = "10")
    private Integer stockQuantity;

    public Integer getStockQuantity() {
        return stockQuantity;
    }
}
