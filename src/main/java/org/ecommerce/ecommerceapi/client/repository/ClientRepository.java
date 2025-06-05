package org.ecommerce.ecommerceapi.client.repository;

import org.ecommerce.ecommerceapi.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email); // ✅ Adicione isso
}
