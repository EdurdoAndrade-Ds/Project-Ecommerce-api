package org.ecommerce.ecommerceapi.modules.client.repositories;

import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void deveBuscarClientePorUsername() {
        ClientEntity cliente = new ClientEntity();
        cliente.setUsername("usuario_teste");
        cliente.setEmail("teste@email.com");
        cliente.setName("Usuário Teste");
        cliente.setPassword("senhaValida123"); // Corrigido
        cliente.setActive(true);

        clientRepository.save(cliente);
        var encontrado = clientRepository.findByUsername("usuario_teste");

        assertTrue(encontrado.isPresent());
        assertEquals("usuario_teste", encontrado.get().getUsername());
    }
}
