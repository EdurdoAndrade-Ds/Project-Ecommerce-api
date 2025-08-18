package org.ecommerce.ecommerceapi.modules.order.repository;

import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.ecommerce.ecommerceapi.modules.order.entity.Order;
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
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    private ClientEntity cliente1;
    private ClientEntity cliente2;

    private Order order1Cliente1;
    private Order order2Cliente1;
    private Order orderCliente2;

    @BeforeEach
    void setup() {
        // Criar e salvar clientes
        cliente1 = ClientEntity.builder()
            .name("Cliente 1")
            .username("cliente1")
            .email("cliente1@email.com")
            .password("senha123") // válido (8+ caracteres)
            .build();
        cliente1 = clientRepository.save(cliente1); // salvar no banco

        cliente2 = ClientEntity.builder()
            .name("Cliente 2")
            .username("cliente2")
            .email("cliente2@email.com")
            .password("senha123") // válido (8+ caracteres)
            .build();
        cliente2 = clientRepository.save(cliente2);

        // Criar e salvar pedidos
        order1Cliente1 = new Order();
        order1Cliente1.setCliente(cliente1);
        order1Cliente1.setCancelado(false);
        order1Cliente1.setDateCreate(LocalDateTime.now().minusDays(1));
        order1Cliente1.setTotal(BigDecimal.valueOf(150.0));
        order1Cliente1 = orderRepository.save(order1Cliente1);

        order2Cliente1 = new Order();
        order2Cliente1.setCliente(cliente1);
        order2Cliente1.setCancelado(true);
        order2Cliente1.setDateCreate(LocalDateTime.now());
        order2Cliente1.setTotal(BigDecimal.valueOf(300.0));
        order2Cliente1 = orderRepository.save(order2Cliente1);

        orderCliente2 = new Order();
        orderCliente2.setCliente(cliente2);
        orderCliente2.setCancelado(false);
        orderCliente2.setDateCreate(LocalDateTime.now());
        orderCliente2.setTotal(BigDecimal.valueOf(200.0));
        orderCliente2 = orderRepository.save(orderCliente2);
    }

    @Test
    void testFindByClienteId() {
        List<Order> pedidosCliente1 = orderRepository.findByClienteId(cliente1.getId());
        assertEquals(2, pedidosCliente1.size(), "Deve haver 2 pedidos para o Cliente 1");

        List<Order> pedidosCliente2 = orderRepository.findByClienteId(cliente2.getId());
        assertEquals(1, pedidosCliente2.size(), "Deve haver 1 pedido para o Cliente 2");
    }

    @Test
    void testFindByIdAndClienteId() {
        Optional<Order> pedidoOpt = orderRepository.findByIdAndClienteId(order1Cliente1.getId(), cliente1.getId());
        assertTrue(pedidoOpt.isPresent(), "O pedido deve ser encontrado para o Cliente 1");

        pedidoOpt = orderRepository.findByIdAndClienteId(orderCliente2.getId(), cliente1.getId());
        assertTrue(pedidoOpt.isEmpty(), "O pedido não deve ser encontrado para o Cliente 1");
    }

    @Test
    void testFindByClienteIdAndCanceladoTrue() {
        List<Order> canceladosCliente1 = orderRepository.findByClienteIdAndCanceladoTrue(cliente1.getId());
        assertEquals(1, canceladosCliente1.size(), "Deve haver 1 pedido cancelado para o Cliente 1");

        List<Order> canceladosCliente2 = orderRepository.findByClienteIdAndCanceladoTrue(cliente2.getId());
        assertTrue(canceladosCliente2.isEmpty(), "Não deve haver pedidos cancelados para o Cliente 2");
    }
}
