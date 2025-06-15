package org.ecommerce.ecommerceapi.modules.pedido.repository;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private ClienteEntity cliente1;
    private ClienteEntity cliente2;

    private Pedido pedido1Cliente1;
    private Pedido pedido2Cliente1;
    private Pedido pedidoCliente2;

    @BeforeEach
    void setup() {
        // Criar e salvar clientes
        cliente1 = ClienteEntity.builder()
            .nome("Cliente 1")
            .username("cliente1")
            .email("cliente1@email.com")
            .senha("senha123") // válido (8+ caracteres)
            .build();
        cliente1 = clienteRepository.save(cliente1); // salvar no banco

        cliente2 = ClienteEntity.builder()
            .nome("Cliente 2")
            .username("cliente2")
            .email("cliente2@email.com")
            .senha("senha123") // válido (8+ caracteres)
            .build();
        cliente2 = clienteRepository.save(cliente2);

        // Criar e salvar pedidos
        pedido1Cliente1 = new Pedido();
        pedido1Cliente1.setCliente(cliente1);
        pedido1Cliente1.setCancelado(false);
        pedido1Cliente1.setDateCreate(LocalDateTime.now().minusDays(1));
        pedido1Cliente1.setTotal(BigDecimal.valueOf(150.0));
        pedido1Cliente1 = pedidoRepository.save(pedido1Cliente1);

        pedido2Cliente1 = new Pedido();
        pedido2Cliente1.setCliente(cliente1);
        pedido2Cliente1.setCancelado(true);
        pedido2Cliente1.setDateCreate(LocalDateTime.now());
        pedido2Cliente1.setTotal(BigDecimal.valueOf(300.0));
        pedido2Cliente1 = pedidoRepository.save(pedido2Cliente1);

        pedidoCliente2 = new Pedido();
        pedidoCliente2.setCliente(cliente2);
        pedidoCliente2.setCancelado(false);
        pedidoCliente2.setDateCreate(LocalDateTime.now());
        pedidoCliente2.setTotal(BigDecimal.valueOf(200.0));
        pedidoCliente2 = pedidoRepository.save(pedidoCliente2);
    }

    @Test
    void testFindByClienteId() {
        List<Pedido> pedidosCliente1 = pedidoRepository.findByClienteId(cliente1.getId());
        assertEquals(2, pedidosCliente1.size());

        List<Pedido> pedidosCliente2 = pedidoRepository.findByClienteId(cliente2.getId());
        assertEquals(1, pedidosCliente2.size());
    }

    @Test
    void testFindByIdAndClienteId() {
        Optional<Pedido> pedidoOpt = pedidoRepository.findByIdAndClienteId(pedido1Cliente1.getId(), cliente1.getId());
        assertTrue(pedidoOpt.isPresent());

        pedidoOpt = pedidoRepository.findByIdAndClienteId(pedidoCliente2.getId(), cliente1.getId());
        assertTrue(pedidoOpt.isEmpty());
    }

    @Test
    void testFindByClienteIdAndCanceladoTrue() {
        List<Pedido> canceladosCliente1 = pedidoRepository.findByClienteIdAndCanceladoTrue(cliente1.getId());
        assertEquals(1, canceladosCliente1.size());

        List<Pedido> canceladosCliente2 = pedidoRepository.findByClienteIdAndCanceladoTrue(cliente2.getId());
        assertTrue(canceladosCliente2.isEmpty());
    }
}
