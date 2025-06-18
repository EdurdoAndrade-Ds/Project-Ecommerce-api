package org.ecommerce.ecommerceapi.modules.cliente.dto;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileClienteResponseDTO {
    private String nome;
    private String username;
    private String email;
    private String telefone;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;

    // Implementação do equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Verifica se são o mesmo objeto
        if (!(o instanceof ProfileClienteResponseDTO)) return false; // Verifica se o objeto é da mesma classe
        ProfileClienteResponseDTO that = (ProfileClienteResponseDTO) o; // Faz o cast

        // Compara todos os campos relevantes
        return Objects.equals(nome, that.nome) &&
               Objects.equals(username, that.username) &&
               Objects.equals(email, that.email) &&
               Objects.equals(telefone, that.telefone) &&
               Objects.equals(endereco, that.endereco) &&
               Objects.equals(cidade, that.cidade) &&
               Objects.equals(estado, that.estado) &&
               Objects.equals(cep, that.cep);
    }

    // Implementação do hashCode
    @Override
    public int hashCode() {
        return Objects.hash(nome, username, email, telefone, endereco, cidade, estado, cep);
    }
}
