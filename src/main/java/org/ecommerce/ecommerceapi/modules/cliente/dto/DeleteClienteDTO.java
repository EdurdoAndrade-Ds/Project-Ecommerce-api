package org.ecommerce.ecommerceapi.modules.cliente.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteClienteDTO {
    @NotBlank(message = "A senha é obrigatória para confirmar a deleção")
    private String senha;
}

