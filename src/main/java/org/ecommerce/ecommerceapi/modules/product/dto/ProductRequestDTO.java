package org.ecommerce.ecommerceapi.modules.product.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductRequestDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer estoque;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductRequestDTO)) return false;
        ProductRequestDTO that = (ProductRequestDTO) o;
        return Objects.equals(nome, that.nome) &&
               Objects.equals(descricao, that.descricao) &&
               Objects.equals(preco, that.preco) &&
               Objects.equals(estoque, that.estoque);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, descricao, preco, estoque);
    }

    @Override
    public String toString() {
        return "ProductRequestDTO{" +
               "nome='" + nome + '\'' +
               ", descricao='" + descricao + '\'' +
               ", preco=" + preco +
               ", estoque=" + estoque +
               '}';
    }

    // Método canEqual para verificar igualdade
    protected boolean canEqual(Object other) {
        return other instanceof ProductRequestDTO;
    }
}