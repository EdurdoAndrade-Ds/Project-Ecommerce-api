package org.ecommerce.ecommerceapi.modules.product.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Integer estoque;

    @Column(nullable = false)
    private Double discountPercentage = 0.0;

    private BigDecimal discountedPrice; // Preço com desconto, calculado dinamicamente

    // O campo discountedPrice pode ser removido, pois será calculado dinamicamente
    // private BigDecimal discountedPrice;

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
        return calcularDiscountedPrice();
    }
    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    // Método para calcular o preço com desconto
    public BigDecimal calcularDiscountedPrice() {
        if (discountPercentage != null && discountPercentage > 0) {
            BigDecimal discount = preco.multiply(BigDecimal.valueOf(discountPercentage / 100));
            return preco.subtract(discount);
        }
        return preco; // Se não houver desconto, retorna o preço original
    }
}