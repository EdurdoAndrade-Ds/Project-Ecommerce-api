package org.ecommerce.ecommerceapi.modules.client.repositories;

import java.util.Optional;

import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByUsernameOrEmail(String username, String email);
    Optional<ClientEntity> findByUsername(String username);
    Optional<ClientEntity> findByEmail(String email);
    boolean existsByUsernameOrEmailAndIdNot(String username, String email, Long id);
}

