package org.ecommerce.ecommerceapi.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Deve ser um endereço de e-mail válido")
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\d{10,15}", message = "O telefone deve conter apenas números (10 a 15 dígitos)")
    private String telefone;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres")
    private String senha;
}

