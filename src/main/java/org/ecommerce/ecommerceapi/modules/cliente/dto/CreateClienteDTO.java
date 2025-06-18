package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class CreateClienteDTO {

    @NotBlank(message = "O campo [nome] é obrigatório")
    private String nome;

    @NotBlank(message = "O campo [username] é obrigatório")
    @Pattern(regexp="\\S+", message = "O campo [username] não deve conter espaço")
    private String username;

    @Email(message = "O campo [email] deve conter um email válido")
    @NotBlank(message = "O campo [email] é obrigatório")
    private String email;

    @Length(min=8, max=100, message= "A senha deve ter entre 8 e 100 caracteres")
    @NotBlank(message = "O campo [senha] é obrigatório")
    private String senha;

    private String telefone;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;

    // Implementação do equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Verifica se são o mesmo objeto
        if (!(o instanceof CreateClienteDTO)) return false; // Verifica se o objeto é da mesma classe
        CreateClienteDTO that = (CreateClienteDTO) o; // Faz o cast

        // Compara todos os campos relevantes
        return Objects.equals(nome, that.nome) &&
               Objects.equals(username, that.username) &&
               Objects.equals(email, that.email) &&
               Objects.equals(senha, that.senha) &&
               Objects.equals(telefone, that.telefone) &&
               Objects.equals(endereco, that.endereco) &&
               Objects.equals(cidade, that.cidade) &&
               Objects.equals(estado, that.estado) &&
               Objects.equals(cep, that.cep);
    }

    // Implementação do hashCode
    @Override
    public int hashCode() {
        return Objects.hash(nome, username, email, senha, telefone, endereco, cidade, estado, cep);
    }

    // Getters e Setters
    // (Se não estiver usando Lombok, você pode implementar manualmente)
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
