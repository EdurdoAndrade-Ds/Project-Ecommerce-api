package org.ecommerce.ecommerceapi.inventory.model;

import jakarta.persistence.*;
import lombok.Data;
import org.ecommerce.ecommerceapi.product.model.Product;

@Entity
@Table(name = "inventory")
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, unique = true)
    private Product product;

    @Column(nullable = false)
    private Integer quantity = 0;
}
