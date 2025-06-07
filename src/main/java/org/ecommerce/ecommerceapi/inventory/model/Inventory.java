package org.ecommerce.ecommerceapi.inventory.model;

import jakarta.persistence.*;
import lombok.*;
import org.ecommerce.ecommerceapi.product.model.Product;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
        product.setInventory(this);
    }

    public void setQuantity(Integer stockQuantity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getInventory() {
        return quantity;
    }
}