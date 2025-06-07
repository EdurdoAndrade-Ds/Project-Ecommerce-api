package org.ecommerce.ecommerceapi.client.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteClientDTO {
    @NotBlank(message = "Senha é obrigatória para confirmar exclusão")
    @Schema(description = "Senha do cliente para confirmação de exclusão", example = "minhaSenha123", required = true)
    private String senha;
}