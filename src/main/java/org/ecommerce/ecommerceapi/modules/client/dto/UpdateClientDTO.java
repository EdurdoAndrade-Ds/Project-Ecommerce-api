package org.ecommerce.ecommerceapi.modules.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class UpdateClientDTO {

    private String name;

    @Pattern(regexp="\\S+", message = "O username não deve conter espaço")
    private String username;

    @Email(message = "O email deve ser válido")
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
        if (!(o instanceof UpdateClientDTO)) return false; // Verifica se o objeto é da mesma classe
        UpdateClientDTO that = (UpdateClientDTO) o; // Faz o cast

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
