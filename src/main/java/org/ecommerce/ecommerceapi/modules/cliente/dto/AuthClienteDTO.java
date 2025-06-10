package org.ecommerce.ecommerceapi.modules.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AuthClienteDTO {

    public AuthClienteDTO(String joaosilva, String senha123456) {
    }
    private String username;
    private String senha;
}

