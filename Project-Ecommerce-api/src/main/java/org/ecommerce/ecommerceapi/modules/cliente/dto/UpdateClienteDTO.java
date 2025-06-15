package org.ecommerce.ecommerceapi.modules.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClienteDTO {

    private String nome;

    @Pattern(regexp="\\S+", message = "O username não deve conter espaço")
    private String username;

    @Email(message = "O email deve ser válido")
    private String email;

    private String telefone;

    private String endereco;

    private String cidade;

    private String estado;

    private String cep;
}


