package org.ecommerce.ecommerceapi.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {

    @Schema(description = "ID do cliente que está fazendo o pedido", example = "42", required = true)
    @NotNull(message = "O ID do cliente é obrigatório.")
    private Long customerId;

    @Schema(description = "Lista de IDs dos produtos incluídos no pedido", example = "[101, 102]", required = true)
    @NotNull(message = "A lista de produtos não pode ser nula.")
    @Size(min = 1, message = "O pedido deve conter pelo menos um produto.")
    private List<Long> produtoIds;
}
