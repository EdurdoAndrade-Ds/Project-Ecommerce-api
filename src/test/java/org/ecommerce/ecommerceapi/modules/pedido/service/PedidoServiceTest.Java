package org.ecommerce.ecommerceapi.modules.pedido.service;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoRequestDTO;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoRequestDTO.ItemDTO;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoResponseDTO;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.pedido.repository.PedidoRepository;
import org.ecommerce.ecommerceapi.modules.product.entities.Product;
import org.ecommerce.ecommerceapi.modules.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
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
        cliente = new ClienteEntity();
        cliente.setId(1L);

        produto = new Product();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(BigDecimal.TEN);

        pedidoService = new PedidoService(pedidoRepository, productService, clienteRepository);
    }

    @Test
    void testCriarPedido() {
        PedidoRequestDTO request = new PedidoRequestDTO();
        request.setItens(new ArrayList<>());

        // Simula um item (sem usar ItemPedidoDTO diretamente)
        var mockItem = new Object() {
            Long produtoId = 1L;
            int quantidade = 2;
        };

        // Simula a lista de itens usando lambda/anon class
        request.getItens().add(new ItemDTO() {{
            setProdutoId(mockItem.produtoId);
            setQuantidade(mockItem.quantidade);
        }});

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productService.buscarPorId(1L)).thenReturn(produto);
        when(pedidoRepository.save(any())).thenAnswer(inv -> inv.getArguments()[0]);

        PedidoResponseDTO response = pedidoService.criar(request, 1L);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(20), response.getTotal());
    }

    @Test
    void testListarPorCliente() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.valueOf(30));
        pedido.setItens(List.of());

        when(pedidoRepository.findByClienteId(1L)).thenReturn(List.of(pedido));

        List<PedidoResponseDTO> result = pedidoService.listarPorCliente(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getClienteId());
    }

    @Test
    void testBuscarPorId() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.TEN);
        pedido.setItens(List.of());

        when(pedidoRepository.findByIdAndClienteId(1L, 1L)).thenReturn(Optional.of(pedido));

        PedidoResponseDTO dto = pedidoService.buscarPorId(1L, 1L);

        assertEquals(1L, dto.getClienteId());
    }

    @Test
    void testCancelar() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(cliente);
        pedido.setItens(List.of());

        when(pedidoRepository.findByIdAndClienteId(1L, 1L)).thenReturn(Optional.of(pedido));

        pedidoService.cancelar(1L, 1L);

        assertTrue(pedido.isCancelado());
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void testHistorico() {
        Pedido cancelado = new Pedido();
        cancelado.setId(1L);
        cancelado.setCliente(cliente);
        cancelado.setCancelado(true);
        cancelado.setItens(List.of());

        when(pedidoRepository.findByClienteIdAndCanceladoTrue(1L)).thenReturn(List.of(cancelado));

        List<PedidoResponseDTO> historico = pedidoService.historico(1L);

        assertEquals(1, historico.size());
        assertEquals(1L, historico.get(0).getClienteId());
    }

    
}
