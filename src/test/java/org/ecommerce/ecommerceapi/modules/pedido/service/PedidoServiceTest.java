package org.ecommerce.ecommerceapi.modules.pedido.service;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoRequestDTO;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoResponseDTO;
import org.ecommerce.ecommerceapi.modules.pedido.entity.ItemPedido;
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
import java.time.LocalDateTime;
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
    void testListarPorCliente() {
        Pedido pedido = new Pedido();
        pedido.setId(100L);
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.valueOf(150.00));
        pedido.setItens(new ArrayList<>());
        when(pedidoRepository.findByClienteId(cliente.getId())).thenReturn(List.of(pedido));
        List<PedidoResponseDTO> responseList = pedidoService.listarPorCliente(cliente.getId());
        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        PedidoResponseDTO dto = responseList.get(0);
        assertEquals(pedido.getId(), dto.getId());
        assertEquals(cliente.getId(), dto.getClienteId());
        assertEquals(pedido.getTotal(), dto.getTotal());
    }

    @Test
    void testHistorico() {
        Pedido pedidoCancelado = new Pedido();
        pedidoCancelado.setId(200L);
        pedidoCancelado.setCliente(cliente);
        pedidoCancelado.setTotal(BigDecimal.valueOf(200.00));
        pedidoCancelado.setCancelado(true);
        pedidoCancelado.setItens(new ArrayList<>());
        when(pedidoRepository.findByClienteIdAndCanceladoTrue(cliente.getId())).thenReturn(List.of(pedidoCancelado));
        List<PedidoResponseDTO> historicoList = pedidoService.historico(cliente.getId());
        assertNotNull(historicoList);
        assertEquals(1, historicoList.size());
        PedidoResponseDTO dto = historicoList.get(0);
        assertEquals(pedidoCancelado.getId(), dto.getId());
        assertEquals(cliente.getId(), dto.getClienteId());
        assertEquals(pedidoCancelado.getTotal(), dto.getTotal());
    }

    
    @Test
    void testEqualsAndHashCode() {
        PedidoService service1 = new PedidoService(pedidoRepository, productService, clienteRepository);
        PedidoService service2 = new PedidoService(pedidoRepository, productService, clienteRepository);

        assertEquals(service1, service2);
        assertEquals(service1.hashCode(), service2.hashCode());
    }

        @Test
    void testBuscarPorIdDTO_Sucesso() {
        Pedido pedido = new Pedido();
        pedido.setId(123L);
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.valueOf(100));
        pedido.setDateCreate(LocalDateTime.now());
        pedido.setItens(new ArrayList<>());

        when(pedidoRepository.findByIdAndClienteId(123L, 1L)).thenReturn(Optional.of(pedido));

        PedidoResponseDTO dto = pedidoService.buscarPorId(123L, 1L);

        assertNotNull(dto);
        assertEquals(123L, dto.getId());
        assertEquals(1L, dto.getClienteId());
        assertEquals(BigDecimal.valueOf(100), dto.getTotal());
    }

    @Test
    void testBuscarPorIdDTO_PedidoNaoEncontrado() {
        when(pedidoRepository.findByIdAndClienteId(999L, 1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.buscarPorId(999L, 1L);
        });

        assertEquals("Pedido não encontrado ou acesso negado", exception.getMessage());
    }

    
    
    @Test
    void testToString() {
        PedidoService service = new PedidoService(pedidoRepository, productService, clienteRepository);
        String str = service.toString();
        assertNotNull(str);
        assertTrue(str.contains("pedidoRepository"));
        assertTrue(str.contains("productService"));
        assertTrue(str.contains("clienteRepository"));
    }

    

    @Test
    void testCriar() {
        PedidoRequestDTO request = new PedidoRequestDTO();
        List<PedidoRequestDTO.ItemDTO> itens = new ArrayList<>();
        PedidoRequestDTO.ItemDTO itemDTO = new PedidoRequestDTO.ItemDTO();
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
    void testSetPedidoRepository() {
        PedidoRepository newRepository = mock(PedidoRepository.class);
        pedidoService.setPedidoRepository(newRepository);
        assertEquals(newRepository, pedidoService.getPedidoRepository());
    }

    @Test
    void testSetProductService() {
        ProductService newService = mock(ProductService.class);
        pedidoService.setProductService(newService);
        assertEquals(newService, pedidoService.getProductService());
    }

    @Test
    void testSetClienteRepository() {
        ClienteRepository newRepository = mock(ClienteRepository.class);
        pedidoService.setClienteRepository(newRepository);
        assertEquals(newRepository, pedidoService.getClienteRepository());
    }

    @Test
    void testCanEqual() {
        PedidoService service = new PedidoService(pedidoRepository, productService, clienteRepository);
        assertTrue(service.canEqual(new PedidoService(pedidoRepository, productService, clienteRepository)));
        assertFalse(service.canEqual(new Object()));
    }
    public boolean canEqual(Object other) {
        return other instanceof PedidoService;
    }
    @Test
    void testCriar_ClienteNaoEncontrado() {
        PedidoRequestDTO request = new PedidoRequestDTO();
        List<PedidoRequestDTO.ItemDTO> itens = new ArrayList<>();
        PedidoRequestDTO.ItemDTO itemDTO = new PedidoRequestDTO.ItemDTO();
        itemDTO.setProdutoId(produto.getId());
        itemDTO.setQuantidade(3);
        itens.add(itemDTO);
        request.setItens(itens);

        // Simula que o cliente não foi encontrado
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.criar(request, cliente.getId());
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
    }

    @Test
    void testBuscarPorId_PedidoNaoEncontrado() {
        // Simula que o pedido não foi encontrado
        when(pedidoRepository.findByIdAndClienteId(999L, cliente.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.buscarPorId(999L, cliente.getId());
        });

        assertEquals("Pedido não encontrado ou acesso negado", exception.getMessage());
    }


}