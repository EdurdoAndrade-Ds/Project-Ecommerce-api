package org.ecommerce.ecommerceapi.modules.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthClienteDTO {


    private String username;
    private String senha;
}