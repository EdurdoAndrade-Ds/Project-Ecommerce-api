package org.ecommerce.ecommerceapi.modules.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_product")
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
    private BigDecimal discountedPrice; // Preço com desconto, calculado dinamicamente

    // O campo discountedPrice pode ser removido, pois será calculado dinamicamente
    // private BigDecimal discountedPrice;


    // Método para calcular o preço com desconto


    public BigDecimal getDiscountedPrice() {
        if (price == null) return null;
        // se desconto for nulo ou zero -> retorna o MESMO BigDecimal (mantém escala, ex.: 100.0)
        if (discountPercentage == null || BigDecimal.valueOf(discountPercentage).signum() == 0) {
            return price;
        }
        BigDecimal pct = BigDecimal.valueOf(discountPercentage);
        BigDecimal discount = price.multiply(pct).divide(BigDecimal.valueOf(100));
        return price.subtract(discount);
    }
}