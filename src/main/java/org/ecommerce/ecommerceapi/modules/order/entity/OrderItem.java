package org.ecommerce.ecommerceapi.modules.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

import org.ecommerce.ecommerceapi.modules.product.entity.Product;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of = {"id","nameProduct","quantity","unitPrice","discountPrice","pricePad"})
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameProduct;
    private Integer quantity;
    private BigDecimal unitPrice; // Preço unitário (pode ser com ou sem desconto)

    @Column(name = "discount_price") // name opcional para a coluna
    private BigDecimal discountPrice; // Preço com desconto

    private BigDecimal pricePad; // Preço total pago (quantidade * preço unitário com desconto)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Product product;

}
