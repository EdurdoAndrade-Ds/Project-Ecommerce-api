package org.ecommerce.ecommerceapi.modules.product.entities;

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
    private Double descountPercentage = 0.0; 

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
    public Double getDescontoPercentual() {
        return descountPercentage;
    }
    public void setDescontoPercentual(Double discountPercentage) {
        this.descountPercentage = discountPercentage;
    }
    public BigDecimal getDescountPrice() {
        return descountedPrice;
    }
    public void setDescountedPrice(BigDecimal discountedPrice) {
        this.descountedPrice = discountedPrice;
    }
}
