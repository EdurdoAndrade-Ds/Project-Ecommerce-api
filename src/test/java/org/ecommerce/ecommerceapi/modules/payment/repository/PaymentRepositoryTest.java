package org.ecommerce.ecommerceapi.modules.payment.repository;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.ecommerce.ecommerceapi.modules.payment.entity.Payment;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.pedido.repository.PedidoRepository;
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
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        paymentRepository.deleteAll();
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Test
    void testFindByPedidoId() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setUsername("cliente1");
        cliente.setEmail("cliente1@test.com");
        cliente.setNome("Cliente");
        cliente.setSenha("senha123");
        cliente.setAtivo(true);
        cliente = clienteRepository.save(cliente);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setCancelado(false);
        pedido.setTotal(BigDecimal.valueOf(100));
        pedido.setDateCreate(LocalDateTime.now());
        pedido = pedidoRepository.save(pedido);

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
