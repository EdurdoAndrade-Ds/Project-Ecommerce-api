package org.ecommerce.ecommerceapi.modules.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class UpdateClienteDTO {

    private String nome;

    @Pattern(regexp="\\S+", message = "O username não deve conter espaço")
    private String username;

    @Email(message = "O email deve ser válido")
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
        if (!(o instanceof UpdateClienteDTO)) return false; // Verifica se o objeto é da mesma classe
        UpdateClienteDTO that = (UpdateClienteDTO) o; // Faz o cast

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

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
