package org.ecommerce.ecommerceapi.modules.pedido.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

public class PedidoRepositoryTest {

    @Autowired
    private org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository clienteRepository;

    @Test
<<<<<<< HEAD
    @DisplayName("Deve encontrar pedidos por clienteId")
    void testFindByClienteId() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Cliente Teste");
        cliente.setEmail("cliente@teste.com");
        cliente.setSenha("123456");
        cliente = clienteRepository.save(cliente); // Supondo que o ID seja definido manualmente (ex: mockado)

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.valueOf(100));
        pedido.setDateCreate(LocalDateTime.now());

        pedidoRepository.save(pedido);

        List<Pedido> pedidos = pedidoRepository.findByClienteId(1L);

        assertThat(pedidos).isNotEmpty();
        assertThat(pedidos.get(0).getCliente().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Deve encontrar pedido por id e clienteId")
    void testFindByIdAndClienteId() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(2L);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.valueOf(50));
        pedido.setDateCreate(LocalDateTime.now());

        Pedido saved = pedidoRepository.save(pedido);

        Optional<Pedido> encontrado = pedidoRepository.findByIdAndClienteId(saved.getId(), 2L);

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getCliente().getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("Deve encontrar pedidos cancelados por clienteId")
    void testFindByClienteIdAndCanceladoTrue() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(3L);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.valueOf(75));
        pedido.setDateCreate(LocalDateTime.now());
        pedido.setCancelado(true);

        pedidoRepository.save(pedido);

        List<Pedido> cancelados = pedidoRepository.findByClienteIdAndCanceladoTrue(3L);

        assertThat(cancelados).hasSize(1);
        assertThat(cancelados.get(0).isCancelado()).isTrue();
=======
    void testRepositoryMock() {
        PedidoRepository repo = Mockito.mock(PedidoRepository.class);
        assertNotNull(repo);
>>>>>>> ab764e7c142e15083dc06c3d77cc7a4ac77f6659
    }
}
