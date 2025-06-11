package org.ecommerce.ecommerceapi.modules.pedido.repository;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PedidoStatusTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository clienteRepository;

    @Test
    @DisplayName("Deve salvar e recuperar um pedido simples")
    void testSalvarPedidoSemStatus() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Cliente Teste");
        cliente.setEmail("cliente@teste.com");
        cliente.setSenha("123456");
        cliente = clienteRepository.save(cliente); // simulação

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.valueOf(100));
        pedido.setDateCreate(LocalDateTime.now());

        Pedido saved = pedidoRepository.save(pedido);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCliente().getId()).isEqualTo(1L);
        assertThat(saved.getTotal()).isEqualByComparingTo("100");
    }
}
