package org.ecommerce.ecommerceapi.modules.product.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer estoque;
    private Double discountPercentage;
    private BigDecimal discountedPrice;

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

    public Double getDiscountPercentage() {
        return discountPercentage;
    }
    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }
    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
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
               ", name='" + nome + '\'' +
               ", descricao='" + descricao + '\'' +
               ", price=" + preco +
               ", stock=" + estoque +
               '}';
    }

    // Método canEqual para verificar igualdade
    protected boolean canEqual(Object other) {
        return other instanceof ProductResponseDTO;
    }

    /**
     * Ajusta o percentual de desconto do produto e recalcula o valor com desconto
     * se o preço estiver definido.
     *
     * @param discountPercentage percentual de desconto a ser aplicado
     */

    public void setDiscountPercentage(double discountPercentage) {


        this.discountPercentage = discountPercentage;

        if (this.preco != null) {
            BigDecimal newPrice = this.preco.multiply(
                    BigDecimal.valueOf(1 - (discountPercentage / 100.0)));
            this.discountedPrice = newPrice;
        }
    }
}