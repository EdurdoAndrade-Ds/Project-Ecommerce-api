package org.ecommerce.ecommerceapi.modules.product.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductUpdateDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;

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

    // equals e hashCode baseados em todos os campos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductUpdateDTO)) return false;
        ProductUpdateDTO that = (ProductUpdateDTO) o;
        return Objects.equals(nome, that.nome) &&
               Objects.equals(descricao, that.descricao) &&
               Objects.equals(preco, that.preco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, descricao, preco);
    }

    // toString legível
    @Override
    public String toString() {
        return "ProductUpdateDTO{" +
               "nome='" + nome + '\'' +
               ", descricao='" + descricao + '\'' +
               ", preco=" + preco +
               '}';
    }

    // canEqual para proteger contra heranças erradas no equals
    protected boolean canEqual(Object other) {
        return other instanceof ProductUpdateDTO;
    }
}
