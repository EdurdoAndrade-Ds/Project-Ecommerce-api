package org.ecommerce.ecommerceapi.modules.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "tb_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    // Porcentagem de desconto 0–100, precisa ser BigDecimal
    @Column(name = "discount_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal discountPercentage = BigDecimal.ZERO;



    @Transient
    private BigDecimal discountedPrice;

    public BigDecimal getDiscountedPrice() {
        if (discountedPrice != null) {
            return discountedPrice.setScale(2, RoundingMode.HALF_UP);
        }
        if (price == null) {
            return null;
        }
        if (discountPercentage == null || discountPercentage.compareTo(BigDecimal.ZERO) == 0) {
            return price.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal desconto = price.multiply(discountPercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return price.subtract(desconto).setScale(2, RoundingMode.HALF_UP);
    }
}