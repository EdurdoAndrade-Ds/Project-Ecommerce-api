package org.ecommerce.ecommerceapi.client.repository;

import org.ecommerce.ecommerceapi.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByEmailContaining(String email);
}
