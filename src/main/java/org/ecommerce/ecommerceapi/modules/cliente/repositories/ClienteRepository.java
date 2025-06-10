package org.ecommerce.ecommerceapi.modules.cliente.repositories;

import java.util.Optional;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByUsernameOrEmail(String username, String email);
    Optional<ClienteEntity> findByUsername(String username);
    Optional<ClienteEntity> findByEmail(String email);
}

