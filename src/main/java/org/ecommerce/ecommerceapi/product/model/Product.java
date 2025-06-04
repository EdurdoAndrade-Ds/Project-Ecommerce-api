package org.ecommerce.ecommerceapi.product.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

import org.ecommerce.ecommerceapi.inventory.model.Inventory;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Inventory inventory;

}
