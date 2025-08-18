package org.ecommerce.ecommerceapi.modules.payment.repository;

import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.ecommerce.ecommerceapi.modules.payment.entity.Payment;
import org.ecommerce.ecommerceapi.modules.order.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        paymentRepository.deleteAll();
        orderRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void testFindByPedidoId() {
        ClientEntity cliente = new ClientEntity();
        cliente.setUsername("cliente1");
        cliente.setEmail("cliente1@test.com");
        cliente.setName("Cliente");
        cliente.setPassword("senha123");
        cliente.setActive(true);
        cliente = clientRepository.save(cliente);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setCancelado(false);
        pedido.setTotal(BigDecimal.valueOf(100));
        pedido.setDateCreate(LocalDateTime.now());
        pedido = orderRepository.save(pedido);

        Payment payment = new Payment();
        payment.setPedido(pedido);
        payment.setValor(BigDecimal.valueOf(100));
        payment.setDataPagamento(LocalDateTime.now());
        paymentRepository.save(payment);

        List<Payment> list = paymentRepository.findByPedidoId(pedido.getId());
        assertEquals(1, list.size());
        assertEquals(BigDecimal.valueOf(100), list.get(0).getValor());
    }
}
