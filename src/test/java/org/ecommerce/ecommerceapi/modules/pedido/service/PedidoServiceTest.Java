package org.ecommerce.ecommerceapi.modules.pedido.service;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoRequestDTO;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoResponseDTO;
import org.ecommerce.ecommerceapi.modules.pedido.entity.ItemPedido;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.pedido.repository.PedidoRepository;
import org.ecommerce.ecommerceapi.modules.product.entities.Product;
import org.ecommerce.ecommerceapi.modules.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    private PedidoService pedidoService;
    private PedidoRepository pedidoRepository;
    private ClienteRepository clienteRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() throws Exception {
        pedidoRepository = mock(PedidoRepository.class);
        clienteRepository = mock(ClienteRepository.class);
        productService = mock(ProductService.class);

        pedidoService = new PedidoService();

        var f1 = PedidoService.class.getDeclaredField("pedidoRepository");
        f1.setAccessible(true);
        f1.set(pedidoService, pedidoRepository);

        var f2 = PedidoService.class.getDeclaredField("clienteRepository");
        f2.setAccessible(true);
        f2.set(pedidoService, clienteRepository);

        var f3 = PedidoService.class.getDeclaredField("productService");
        f3.setAccessible(true);
        f3.set(pedidoService, productService);
    }

    @Test
    void criar_DeveCriarPedidoComSucesso() {
        Long clienteId = 1L;

        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(clienteId);
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        Product produto = new Product();
        produto.setId(100L);
        produto.setNome("Produto Teste");
        produto.setPreco(BigDecimal.valueOf(10));
        when(productService.buscarPorId(100L)).thenReturn(produto);

        PedidoRequestDTO.ItemDTO itemDTO = new PedidoRequestDTO.ItemDTO();
        itemDTO.setProdutoId(100L);
        itemDTO.setQuantidade(2);

        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setItens(List.of(itemDTO));

        Pedido pedidoSalvo = new Pedido();
        pedidoSalvo.setId(999L);
        pedidoSalvo.setCliente(cliente);
        pedidoSalvo.setTotal(BigDecimal.valueOf(20));
        pedidoSalvo.setItens(List.of()); // para simplificar
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoSalvo);

        PedidoResponseDTO response = pedidoService.criar(dto, clienteId);

        assertNotNull(response);
        assertEquals(999L, response.getId());
        assertEquals(clienteId, response.getClienteId());
        assertEquals(BigDecimal.valueOf(20), response.getTotal());

        verify(clienteRepository).findById(clienteId);
        verify(productService).buscarPorId(100L);
        verify(pedidoRepository).save(any(Pedido.class));
    }

    @Test
    void criar_ClienteNaoEncontrado_DeveLancarExcecao() {
        Long clienteId = 1L;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        PedidoRequestDTO dto = new PedidoRequestDTO();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            pedidoService.criar(dto, clienteId);
        });

        assertEquals("Cliente não encontrado", ex.getMessage());
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    void listarPorCliente_DeveRetornarLista() {
        Long clienteId = 1L;

        Pedido pedido = new Pedido();
        pedido.setId(123L);
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(clienteId);
        pedido.setCliente(cliente);

        when(pedidoRepository.findByClienteId(clienteId)).thenReturn(List.of(pedido));

        var lista = pedidoService.listarPorCliente(clienteId);
        assertFalse(lista.isEmpty());
        assertEquals(clienteId, lista.get(0).getClienteId());
    }

    @Test
    void buscarPorId_DeveRetornarPedido() {
        Long clienteId = 1L;
        Long pedidoId = 10L;

        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(clienteId);
        pedido.setCliente(cliente);

        when(pedidoRepository.findByIdAndClienteId(pedidoId, clienteId)).thenReturn(Optional.of(pedido));

        var response = pedidoService.buscarPorId(pedidoId, clienteId);
        assertNotNull(response);
        assertEquals(pedidoId, response.getId());
        assertEquals(clienteId, response.getClienteId());
    }

    @Test
    void buscarPorId_NaoEncontrado_DeveLancarExcecao() {
        when(pedidoRepository.findByIdAndClienteId(anyLong(), anyLong())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            pedidoService.buscarPorId(1L, 1L);
        });

        assertEquals("Pedido não encontrado ou acesso negado", ex.getMessage());
    }

    @Test
    void cancelar_DeveCancelarPedido() {
        Long clienteId = 1L;
        Long pedidoId = 10L;

        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);
        pedido.setCancelado(false);
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(clienteId);
        pedido.setCliente(cliente);

        when(pedidoRepository.findByIdAndClienteId(pedidoId, clienteId)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        pedidoService.cancelar(pedidoId, clienteId);

        assertTrue(pedido.isCancelado());
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void historico_DeveRetornarPedidosCancelados() {
        Long clienteId = 1L;

        Pedido pedido = new Pedido();
        pedido.setId(123L);
        pedido.setCancelado(true);
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(clienteId);
        pedido.setCliente(cliente);

        when(pedidoRepository.findByClienteIdAndCanceladoTrue(clienteId)).thenReturn(List.of(pedido));

        var lista = pedidoService.historico(clienteId);
        assertFalse(lista.isEmpty());
        assertTrue(lista.get(0).getItens().isEmpty() || lista.get(0).getItens() != null); // Só garantir que mapeou
    }
}
