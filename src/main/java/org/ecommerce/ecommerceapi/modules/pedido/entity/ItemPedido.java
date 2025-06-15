package org.ecommerce.ecommerceapi.modules.pedido.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

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

    @Column(name = "descount_price") // nome opcional para a coluna
    private BigDecimal descountPrice; // Preço com desconto

    private BigDecimal precoPago; // Preço total pago (quantidade * preço unitário com desconto)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Product produto;

    // Lombok @Data já gera os getters e setters
    // Se não usar Lombok, implemente getters e setters para as novas propriedades
}