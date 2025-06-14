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
    private BigDecimal DescountPriceUnitario; // <- Aqui ficará o valor com ou sem desconto

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Product produto;
}