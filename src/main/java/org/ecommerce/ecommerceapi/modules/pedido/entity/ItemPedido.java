package org.ecommerce.ecommerceapi.modules.pedido.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

import org.ecommerce.ecommerceapi.modules.product.entity.Product;

@Entity
@Data
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeProduto;
    private Integer quantidade;
    private BigDecimal precoUnitario; // Preço unitário (pode ser com ou sem desconto)

    @Column(name = "discount_price") // nome opcional para a coluna
    private BigDecimal discountPrice; // Preço com desconto

    private BigDecimal precoPago; // Preço total pago (quantidade * preço unitário com desconto)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Product produto;

    // Lombok @Data já gera os getters e setters
    // Se não usar Lombok, implemente getters e setters para as novas propriedades
    
    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getPrecoPago() {
        return precoPago;
    }

    public void setPrecoPago(BigDecimal precoPago) {
        this.precoPago = precoPago;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Product getProduto() {
        return produto;
    }

    public void setProduto(Product produto) {
        this.produto = produto;
    }

    // Implementação do equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Verifica se são o mesmo objeto
        if (!(o instanceof ItemPedido)) return false; // Verifica se o objeto é da mesma classe
        ItemPedido that = (ItemPedido) o; // Faz o cast

        // Compara todos os campos relevantes
        return Objects.equals(id, that.id) &&
               Objects.equals(nomeProduto, that.nomeProduto) &&
               Objects.equals(quantidade, that.quantidade) &&
               Objects.equals(precoUnitario, that.precoUnitario) &&
               Objects.equals(discountPrice, that.discountPrice) &&
               Objects.equals(precoPago, that.precoPago) &&
               Objects.equals(pedido, that.pedido) &&
               Objects.equals(produto, that.produto);
    }

    // Implementação do hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, nomeProduto, quantidade, precoUnitario, discountPrice, precoPago, pedido, produto);
    }
}
