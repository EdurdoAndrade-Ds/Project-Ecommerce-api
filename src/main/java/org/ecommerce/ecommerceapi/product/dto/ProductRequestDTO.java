package org.ecommerce.ecommerceapi.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "DTO para criação ou atualização de um produto")
public class ProductRequestDTO {

    @Schema(description = "Nome do produto", example = "Notebook Lenovo")
    private String name;

    @Schema(description = "Descrição do produto", example = "Notebook com 16GB RAM e SSD 512GB")
    private String description;

    @Schema(description = "Preço do produto", example = "3799.99")
    private BigDecimal price;

    @Schema(description = "Quantidade em estoque", example = "10")
    private Integer stockQuantity;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Integer getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
