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

    @Test
    @DisplayName("Deve salvar e recuperar um pedido básico sem status")
    void testSalvarEPersistirPedido() {
        // Cliente fictício (não salvo de fato, apenas mockando o ID)
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(10L);

        // Criar pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.valueOf(200));
        pedido.setDateCreate(LocalDateTime.now());

        // Salvar
        Pedido saved = pedidoRepository.save(pedido);

        // Verificar persistência
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCliente().getId()).isEqualTo(10L);
        assertThat(saved.getTotal()).isEqualByComparingTo("200");
        assertThat(saved.isCancelado()).isFalse();
    }
}

