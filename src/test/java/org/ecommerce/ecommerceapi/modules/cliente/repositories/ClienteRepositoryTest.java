package org.ecommerce.ecommerceapi.modules.cliente.repositories;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void deveBuscarClientePorUsername() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setUsername("usuario_teste");
        cliente.setEmail("teste@email.com");
        cliente.setNome("Usuário Teste");
        cliente.setSenha("senhaValida123"); // Corrigido
        cliente.setAtivo(true);

        clienteRepository.save(cliente);
        var encontrado = clienteRepository.findByUsername("usuario_teste");

        assertTrue(encontrado.isPresent());
        assertEquals("usuario_teste", encontrado.get().getUsername());
    }
}
