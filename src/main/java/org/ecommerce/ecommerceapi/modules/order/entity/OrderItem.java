package org.ecommerce.ecommerceapi.modules.order.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import org.ecommerce.ecommerceapi.modules.product.entity.Product;

@Entity
@Data
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameProduct;

    private Integer quantity;
    private BigDecimal unitPrice; // Preço unitário (pode ser com ou sem desconto)

    @Column(name = "discount_price") // nome opcional para a coluna
    private BigDecimal discountPrice; // Preço com desconto

    private BigDecimal pricePad; // Preço total pago (quantidade * preço unitário com desconto)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(getId(), orderItem.getId()) && Objects.equals(getNameProduct(), orderItem.getNameProduct()) && Objects.equals(getQuantity(), orderItem.getQuantity()) && Objects.equals(getUnitPrice(), orderItem.getUnitPrice()) && Objects.equals(getDiscountPrice(), orderItem.getDiscountPrice()) && Objects.equals(getPricePad(), orderItem.getPricePad()) && Objects.equals(getPedido(), orderItem.getPedido()) && Objects.equals(getProduct(), orderItem.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNameProduct(), getQuantity(), getUnitPrice(), getDiscountPrice(), getPricePad(), getPedido(), getProduct());
    }
}
