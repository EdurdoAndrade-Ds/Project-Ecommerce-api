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
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProductService productService;

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
        pedidoSalvo.setItens(List.of()); // Simplificado
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
        assertNotNull(lista.get(0).getItens());
    }

    @Test
    void mapToResponseDTO_DeveMapearCorretamente() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setTotal(BigDecimal.valueOf(100));
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(10L);
        pedido.setCliente(cliente);

        ItemPedido item = new ItemPedido();
        item.setProduto(new Product(){{
            setId(20L);
        }});
        item.setNomeProduto("Produto X");
        item.setQuantidade(5);
        item.setPrecoUnitario(BigDecimal.valueOf(10));
        pedido.setItens(List.of(item));

        // Usa reflection para acessar o método privado
        var method = PedidoService.class.getDeclaredMethod("mapToResponseDTO", Pedido.class);
        method.setAccessible(true);
        PedidoResponseDTO dto = (PedidoResponseDTO) method.invoke(pedidoService, pedido);

        assertEquals(pedido.getId(), dto.getId());
        assertEquals(cliente.getId(), dto.getClienteId());
        assertEquals(pedido.getTotal(), dto.getTotal());

        assertFalse(dto.getItens().isEmpty());
        var itemDTO = dto.getItens().get(0);
        assertEquals(item.getProduto().getId(), itemDTO.getProdutoId());
        assertEquals(item.getNomeProduto(), itemDTO.getNomeProduto());
        assertEquals(item.getQuantidade(), itemDTO.getQuantidade());
        assertEquals(item.getPrecoUnitario(), itemDTO.getPrecoUnitario());
    }
}
