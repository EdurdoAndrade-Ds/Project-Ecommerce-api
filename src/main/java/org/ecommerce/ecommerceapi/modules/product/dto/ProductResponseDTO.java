package org.ecommerce.ecommerceapi.modules.product.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer estoque;
    private Double descountPercentage; 
    private BigDecimal descountedPrice;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getDescountPercentage() {
        return descountPercentage;
    }
    public void setDescountPercentage(Double descountPercentage) {
        this.descountPercentage = descountPercentage;
    }
    public BigDecimal getDescountedPrice() {
        return descountedPrice;
    }
    public void setDescountedPrice(BigDecimal descountedPrice) {
        this.descountedPrice = descountedPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductResponseDTO)) return false;
        ProductResponseDTO that = (ProductResponseDTO) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(nome, that.nome) &&
               Objects.equals(descricao, that.descricao) &&
               Objects.equals(preco, that.preco) &&
               Objects.equals(estoque, that.estoque);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, preco, estoque);
    }

    @Override
    public String toString() {
        return "ProductResponseDTO{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", descricao='" + descricao + '\'' +
               ", preco=" + preco +
               ", estoque=" + estoque +
               '}';
    }

    // Método canEqual para verificar igualdade
    protected boolean canEqual(Object other) {
        return other instanceof ProductResponseDTO;
    }

    public void setDescontoPercentual(double discountPercentage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDescontoPercentual'");
    }
}