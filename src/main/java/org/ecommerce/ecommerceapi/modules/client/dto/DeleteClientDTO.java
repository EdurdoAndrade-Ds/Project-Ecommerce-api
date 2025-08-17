package org.ecommerce.ecommerceapi.modules.client.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class DeleteClientDTO {
    @NotBlank(message = "A senha é obrigatória para confirmar a deleção")
    private String password;

    public DeleteClientDTO() {
    }

    public DeleteClientDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeleteClientDTO)) return false;
        DeleteClientDTO that = (DeleteClientDTO) o;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    @Override
    public String toString() {
        return "DeleteClienteDTO{" +
                "senha='" + password + '\'' +
                '}';
    }

    public static DeleteClienteDTOBuilder builder() {
        return new DeleteClienteDTOBuilder();
    }

    public static class DeleteClienteDTOBuilder {
        private String password;

        public DeleteClienteDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public DeleteClientDTO build() {
            return new DeleteClientDTO(password);
        }
    }
}
