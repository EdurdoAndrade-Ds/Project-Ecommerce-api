package org.ecommerce.ecommerceapi.modules.pedido.service;
import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoResponseDTO;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.pedido.repository.PedidoRepository;
import org.ecommerce.ecommerceapi.modules.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceRoutesTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProductService productService;

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteEntity cliente;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new ClienteEntity();
        cliente.setId(1L);

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(cliente);
        pedido.setCancelado(false);

        pedidoService = new PedidoService(pedidoRepository, productService, clienteRepository);
    }

    @Test
    void deveCancelarPedidoComSucesso() {
        when(pedidoRepository.findByIdAndClienteId(1L, 1L)).thenReturn(Optional.of(pedido));

        pedidoService.cancelar(1L, 1L);

        assertTrue(pedido.isCancelado());
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    void deveLancarExcecaoAoBuscarPedidoPorIdNaoEncontrado() {
        when(pedidoRepository.findByIdAndClienteId(1L, 1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.buscarPorId(1L, 1L);
        });
        assertEquals("Pedido não encontrado ou acesso negado", exception.getMessage());
    }
    @Test
    void deveRetornarHistoricoDePedidosCancelados() {
        Pedido cancelado = new Pedido();
        cancelado.setId(1L);
        cancelado.setCliente(cliente);
        cancelado.setCancelado(true);
        cancelado.setItens(List.of());  // Inicializa a lista de itens com lista vazia para evitar NullPointer

        when(pedidoRepository.findByClienteIdAndCanceladoTrue(1L)).thenReturn(List.of(cancelado));

        List<PedidoResponseDTO> historico = pedidoService.historico(1L);

        assertEquals(1, historico.size());
        assertEquals(1L, historico.get(0).getClienteId());
    }

    @Test
    void deveRetornarListaVaziaParaHistoricoSemPedidosCancelados() {
        when(pedidoRepository.findByClienteIdAndCanceladoTrue(1L)).thenReturn(List.of());
        List<PedidoResponseDTO> historico = pedidoService.historico(1L);
        assertTrue(historico.isEmpty());
    }
}
