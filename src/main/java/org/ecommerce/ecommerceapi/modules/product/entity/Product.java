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
    private Double descountPercentage = 0.0; 

    private BigDecimal descountedPrice; // Preço com desconto, calculado dinamicamente

    // O campo descountedPrice pode ser removido, pois será calculado dinamicamente
    // private BigDecimal descountedPrice; 

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

    public BigDecimal getDescountedPrice() {
        return calcularDescountedPrice();
    }
    public void setDescountedPrice(BigDecimal descountedPrice) {
        this.descountedPrice = descountedPrice;
    }

    // Método para calcular o preço com desconto
    public BigDecimal calcularDescountedPrice() {
        if (descountPercentage != null && descountPercentage > 0) {
            BigDecimal discount = preco.multiply(BigDecimal.valueOf(descountPercentage / 100));
            return preco.subtract(discount);
        }
        return preco; // Se não houver desconto, retorna o preço original
    }
}
