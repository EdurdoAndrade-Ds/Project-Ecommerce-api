package org.ecommerce.ecommerceapi.modules.client.dto;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthClientResponseDTO {
    private String token;
    private Long id;
    private String username;

    // Implementação do equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Verifica se são o mesmo objeto
        if (!(o instanceof AuthClientResponseDTO)) return false; // Verifica se o objeto é da mesma classe
        AuthClientResponseDTO that = (AuthClientResponseDTO) o; // Faz o cast

        // Compara todos os campos relevantes
        return Objects.equals(token, that.token) &&
               Objects.equals(id, that.id) &&
               Objects.equals(username, that.username);
    }

    // Implementação do hashCode
    @Override
    public int hashCode() {
        return Objects.hash(token, id, username);
    }
}