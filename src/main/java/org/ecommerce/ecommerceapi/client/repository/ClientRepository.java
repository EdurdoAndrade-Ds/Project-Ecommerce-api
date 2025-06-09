package org.ecommerce.ecommerceapi.client.repository;

import org.ecommerce.ecommerceapi.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);

    Optional<Client> findByEmailAndSenha(String email, String senha);

    List<Client> findByNameContainingIgnoreCase(String name);

    boolean existsByEmail(String email);

    @Query("SELECT c FROM Client c WHERE c.telefone LIKE %:telefone%")
    List<Client> findByTelefoneContaining(@Param("telefone") String telefone);

    @Query("SELECT c FROM Client c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :termo, '%')) OR LOWER(c.email) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Client> searchByTerm(@Param("termo") String termo);
}
