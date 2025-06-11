package org.ecommerce.ecommerceapi.modules.product.repository;

import org.ecommerce.ecommerceapi.modules.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
