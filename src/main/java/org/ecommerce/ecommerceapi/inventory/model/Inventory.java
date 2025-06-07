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

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, unique = true)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    public void increase(int amount) {
        if (this.quantity == null) this.quantity = 0;
        this.quantity += amount;
    }

    public void decrease(int amount) {
        if (this.quantity == null || this.quantity < amount) {
            throw new IllegalArgumentException("Estoque insuficiente");
        }
        this.quantity -= amount;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
