package org.ecommerce.ecommerceapi.modules.pedido.service;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoRequestDTO;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoRequestDTO.ItemDTO;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoResponseDTO;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.pedido.repository.PedidoRepository;
import org.ecommerce.ecommerceapi.modules.product.entity.Product;
import org.ecommerce.ecommerceapi.modules.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProductService productService;

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteEntity cliente;
    private Product produto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        cliente = new ClienteEntity();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");
        cliente.setUsername("cliente1");
        cliente.setEmail("cliente1@test.com");
        cliente.setSenha("senha123");

        produto = new Product();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(BigDecimal.valueOf(10.00));
    }

    @Test
    void testCriarPedido() {
        PedidoRequestDTO request = new PedidoRequestDTO();
        List<ItemDTO> itens = new ArrayList<>();
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setProdutoId(produto.getId());
        itemDTO.setQuantidade(3);
        itens.add(itemDTO);
        request.setItens(itens);

        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(productService.buscarPorId(produto.getId())).thenReturn(produto);
        when(pedidoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        PedidoResponseDTO response = pedidoService.criar(request, cliente.getId());

        assertNotNull(response);
        assertEquals(cliente.getId(), response.getClienteId());
        assertEquals(1, response.getItens().size());
        assertEquals(produto.getId(), response.getItens().get(0).getProdutoId());
        assertEquals(BigDecimal.valueOf(30.00), response.getTotal());
    }

    @Test
    void testListarPorCliente() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.valueOf(30));
        pedido.setItens(new ArrayList<>());

        when(pedidoRepository.findByClienteId(cliente.getId())).thenReturn(List.of(pedido));

        List<PedidoResponseDTO> result = pedidoService.listarPorCliente(cliente.getId());

        assertEquals(1, result.size());
        assertEquals(cliente.getId(), result.get(0).getClienteId());
    }

    @Test
    void testBuscarPorId() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.valueOf(10.00));
        pedido.setItens(new ArrayList<>());

        when(pedidoRepository.findByIdAndClienteId(pedido.getId(), cliente.getId())).thenReturn(Optional.of(pedido));

        PedidoResponseDTO dto = pedidoService.buscarPorId(pedido.getId(), cliente.getId());

        assertEquals(cliente.getId(), dto.getClienteId());
    }

    @Test
    void testCancelar() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(cliente);
        pedido.setItens(new ArrayList<>());

        when(pedidoRepository.findByIdAndClienteId(pedido.getId(), cliente.getId())).thenReturn(Optional.of(pedido));

        pedidoService.cancelar(pedido.getId(), cliente.getId());

        assertTrue(pedido.isCancelado());
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void testHistorico() {
        Pedido cancelado = new Pedido();
        cancelado.setId(1L);
        cancelado.setCliente(cliente);
        cancelado.setCancelado(true);
        cancelado.setItens(new ArrayList<>());

        when(pedidoRepository.findByClienteIdAndCanceladoTrue(cliente.getId())).thenReturn(List.of(cancelado));

        List<PedidoResponseDTO> historico = pedidoService.historico(cliente.getId());

        assertEquals(1, historico.size());
        assertEquals(cliente.getId(), historico.get(0).getClienteId());
    }
}
