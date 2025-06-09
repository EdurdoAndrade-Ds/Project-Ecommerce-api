package org.ecommerce.ecommerceapi.client.repository;

import org.ecommerce.ecommerceapi.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByEmail(String email);
}
