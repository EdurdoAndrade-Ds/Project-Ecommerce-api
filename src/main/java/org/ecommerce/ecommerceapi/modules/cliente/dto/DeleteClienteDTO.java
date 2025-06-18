package org.ecommerce.ecommerceapi.modules.cliente.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class DeleteClienteDTO {
    @NotBlank(message = "A senha é obrigatória para confirmar a deleção")
    private String senha;

    public DeleteClienteDTO() {
    }

    public DeleteClienteDTO(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeleteClienteDTO)) return false;
        DeleteClienteDTO that = (DeleteClienteDTO) o;
        return Objects.equals(senha, that.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senha);
    }

    @Override
    public String toString() {
        return "DeleteClienteDTO{" +
                "senha='" + senha + '\'' +
                '}';
    }

    public static DeleteClienteDTOBuilder builder() {
        return new DeleteClienteDTOBuilder();
    }

    public static class DeleteClienteDTOBuilder {
        private String senha;

        public DeleteClienteDTOBuilder senha(String senha) {
            this.senha = senha;
            return this;
        }

        public DeleteClienteDTO build() {
            return new DeleteClienteDTO(senha);
        }
    }
}
