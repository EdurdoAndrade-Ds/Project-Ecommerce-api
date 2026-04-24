package org.ecommerce.ecommerceapi.modules.product.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.ecommerce.ecommerceapi.modules.product.enums.StockOperation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockUpdateRequestDTO {

    @NotNull(message = "Operação é obrigatória")
    private StockOperation operacao;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    private Integer quantidade;
}
