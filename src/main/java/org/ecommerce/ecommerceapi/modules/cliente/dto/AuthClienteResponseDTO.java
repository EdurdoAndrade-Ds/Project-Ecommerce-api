package org.ecommerce.ecommerceapi.modules.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthClienteResponseDTO {
    private String token;
    private Long id;
    private String username;

    public void setId(long l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setUsername(String joaosilva) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setToken(String jwttokenexemplo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

