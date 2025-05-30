package org.ecommerce.ecommerceapi.product.repository;

import com.ecommerce.ecommerce.modules.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Produto, Long> {
    // Aqui podemos adicionar métodos de consulta personalizados se necessário
}
