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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(getId(), orderItem.getId()) && Objects.equals(getNameProduct(), orderItem.getNameProduct()) && Objects.equals(getQuantity(), orderItem.getQuantity()) && Objects.equals(getUnitPrice(), orderItem.getUnitPrice()) && Objects.equals(getDiscountPrice(), orderItem.getDiscountPrice()) && Objects.equals(getPricePad(), orderItem.getPricePad()) && Objects.equals(getOrder(), orderItem.getOrder()) && Objects.equals(getProduct(), orderItem.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNameProduct(), getQuantity(), getUnitPrice(), getDiscountPrice(), getPricePad(), getOrder(), getProduct());
    }
}
