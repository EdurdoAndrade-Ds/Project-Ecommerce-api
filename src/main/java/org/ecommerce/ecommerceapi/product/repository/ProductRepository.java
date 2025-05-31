package org.ecommerce.ecommerceapi.product.repository;

import org.ecommerce.ecommerceapi.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Aqui podemos adicionar métodos de consulta personalizados se necessário
}
