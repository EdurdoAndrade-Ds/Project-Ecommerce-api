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
public class ProfileClientResponseDTO {
    private String name;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String cep;

    // Implementação do equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Verifica se são o mesmo objeto
        if (!(o instanceof ProfileClientResponseDTO)) return false; // Verifica se o objeto é da mesma classe
        ProfileClientResponseDTO that = (ProfileClientResponseDTO) o; // Faz o cast

        // Compara todos os campos relevantes
        return Objects.equals(name, that.name) &&
               Objects.equals(username, that.username) &&
               Objects.equals(email, that.email) &&
               Objects.equals(phone, that.phone) &&
               Objects.equals(address, that.address) &&
               Objects.equals(city, that.city) &&
               Objects.equals(state, that.state) &&
               Objects.equals(cep, that.cep);
    }

    // Implementação do hashCode
    @Override
    public int hashCode() {
        return Objects.hash(name, username, email, phone, address, city, state, cep);
    }
}
