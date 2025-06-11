package org.ecommerce.ecommerceapi.modules.product.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer estoque;
    private BigDecimal precoPromocional;
    private Integer porcentagemDesconto;
    public void setPercentualDesconto(int desconto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPercentualDesconto'");
    }
}
