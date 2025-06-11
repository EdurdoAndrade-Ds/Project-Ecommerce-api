package org.ecommerce.ecommerceapi.modules.pedido.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

public class PedidoStatusTest {

<<<<<<< HEAD
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
=======
    @Test
    void testRepositoryMock() {
        PedidoStatus repo = Mockito.mock(PedidoStatus.class);
        assertNotNull(repo);
>>>>>>> ab764e7c142e15083dc06c3d77cc7a4ac77f6659
    }
}
