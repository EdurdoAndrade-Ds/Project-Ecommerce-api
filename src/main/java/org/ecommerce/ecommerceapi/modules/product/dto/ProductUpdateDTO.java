package org.ecommerce.ecommerceapi.modules.product.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductUpdateDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
}
