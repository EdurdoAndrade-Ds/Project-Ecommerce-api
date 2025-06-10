package org.ecommerce.ecommerceapi.modules.cliente.repositories;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void testFindByUsernameOrEmail() {
        ClienteEntity cliente = ClienteEntity.builder()
                .nome("Teste")
                .username("usuario")
                .email("usuario@email.com")
                .senha("senha1234")
                .build();

        clienteRepository.save(cliente);

        Optional<ClienteEntity> found = clienteRepository.findByUsernameOrEmail("usuario", "usuario@email.com");
        assertTrue(found.isPresent());
        assertEquals("usuario", found.get().getUsername());
    }

    @Test
    void testFindByUsername() {
        ClienteEntity cliente = ClienteEntity.builder()
                .nome("Teste2")
                .username("user2")
                .email("user2@email.com")
                .senha("senha1234")
                .build();

        clienteRepository.save(cliente);

        Optional<ClienteEntity> found = clienteRepository.findByUsername("user2");
        assertTrue(found.isPresent());
        assertEquals("user2", found.get().getUsername());
    }

    @Test
    void testFindByEmail() {
        ClienteEntity cliente = ClienteEntity.builder()
                .nome("Teste3")
                .username("user3")
                .email("user3@email.com")
                .senha("senha1234")
                .build();

        clienteRepository.save(cliente);

        Optional<ClienteEntity> found = clienteRepository.findByEmail("user3@email.com");
        assertTrue(found.isPresent());
        assertEquals("user3@email.com", found.get().getEmail());
    }
}
