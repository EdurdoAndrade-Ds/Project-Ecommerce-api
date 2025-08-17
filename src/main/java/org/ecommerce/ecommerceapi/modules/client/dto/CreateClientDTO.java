package org.ecommerce.ecommerceapi.modules.client.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

@Getter
@Setter
public class CreateClientDTO {

    @NotBlank(message = "O campo [nome] é obrigatório")
    private String name;

    @NotBlank(message = "O campo [username] é obrigatório")
    @Pattern(regexp="\\S+", message = "O campo [username] não deve conter espaço")
    private String username;

    @Email(message = "O campo [email] deve conter um email válido")
    @NotBlank(message = "O campo [email] é obrigatório")
    private String email;

    @Length(min=8, max=100, message= "A senha deve ter entre 8 e 100 caracteres")
    @NotBlank(message = "O campo [senha] é obrigatório")
    private String password;

    @Column(name = "telefone")
    @JsonProperty("phone")
    @JsonAlias({"telefone"}) // aceita payloads antigos
    private String phone;

    @Column(name = "endereco")
    @JsonProperty("address")
    @JsonAlias({"endereco"})
    private String address;

    @Column(name = "cidade")
    @JsonProperty("city")
    @JsonAlias({"cidade"})
    private String city;

    @Column(name = "estado")
    @JsonProperty("state")
    @JsonAlias({"estado"})
    private String state;

    private String cep;

    // Implementação do equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateClientDTO)) return false;
        CreateClientDTO that = (CreateClientDTO) o;

        // Compara todos os campos relevantes
        return Objects.equals(name, that.name) &&
               Objects.equals(username, that.username) &&
               Objects.equals(email, that.email) &&
               Objects.equals(password, that.password) &&
               Objects.equals(phone, that.phone) &&
               Objects.equals(address, that.address) &&
               Objects.equals(city, that.city) &&
               Objects.equals(state, that.state) &&
               Objects.equals(cep, that.cep);
    }

    // Implementação do hashCode
    @Override
    public int hashCode() {
        return Objects.hash(name, username, email, password, phone, address, city, state    , cep);
    }


}
