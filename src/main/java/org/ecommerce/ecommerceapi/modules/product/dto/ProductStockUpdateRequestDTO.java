package org.ecommerce.ecommerceapi.modules.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ecommerce.ecommerceapi.modules.product.enums.OperacaoEstoque;

@Data
public class ProductStockUpdateRequestDTO {

    @Schema(
        description = "Operação de estoque: AUMENTAR ou REDUZIR",
        example = "AUMENTAR"
    )
    private OperacaoEstoque operacao;

    @Schema(
        description = "Quantidade a ser aplicada na operação de estoque",
        example = "10"
    )
    private Integer quantidade;
}
