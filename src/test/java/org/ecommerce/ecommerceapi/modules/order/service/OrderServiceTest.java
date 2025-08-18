package org.ecommerce.ecommerceapi.modules.order.service;

import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.ecommerce.ecommerceapi.modules.order.dto.OrderRequestDTO;
import org.ecommerce.ecommerceapi.modules.order.dto.OrderResponseDTO;
import org.ecommerce.ecommerceapi.modules.order.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.order.repository.OrderRepository;
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


class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private ClientRepository clientRepository;

    private ClientEntity cliente;
    private Product produto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        cliente = new ClientEntity();
        cliente.setId(1L);
        cliente.setName("Cliente Teste");
        cliente.setUsername("cliente1");
        cliente.setEmail("cliente1@test.com");
        cliente.setPassword("senha123");

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
        when(orderRepository.findByClienteId(cliente.getId())).thenReturn(List.of(pedido));
        List<OrderResponseDTO> responseList = orderService.listarPorCliente(cliente.getId());
        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        OrderResponseDTO dto = responseList.get(0);
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
        when(orderRepository.findByClienteIdAndCanceladoTrue(cliente.getId())).thenReturn(List.of(pedidoCancelado));
        List<OrderResponseDTO> historicoList = orderService.history(cliente.getId());
        assertNotNull(historicoList);
        assertEquals(1, historicoList.size());
        OrderResponseDTO dto = historicoList.get(0);
        assertEquals(pedidoCancelado.getId(), dto.getId());
        assertEquals(cliente.getId(), dto.getClienteId());
        assertEquals(pedidoCancelado.getTotal(), dto.getTotal());
    }

    
    @Test
    void testEqualsAndHashCode() {
        OrderService service1 = new OrderService(orderRepository, productService, clientRepository);
        OrderService service2 = new OrderService(orderRepository, productService, clientRepository);

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

        when(orderRepository.findByIdAndClienteId(123L, 1L)).thenReturn(Optional.of(pedido));

        OrderResponseDTO dto = orderService.searchById(123L, 1L);

        assertNotNull(dto);
        assertEquals(123L, dto.getId());
        assertEquals(1L, dto.getClienteId());
        assertEquals(BigDecimal.valueOf(100), dto.getTotal());
    }

    @Test
    void testBuscarPorIdDTO_PedidoNaoEncontrado() {
        when(orderRepository.findByIdAndClienteId(999L, 1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.searchById(999L, 1L);
        });

        assertEquals("Pedido não encontrado ou acesso negado", exception.getMessage());
    }

    
    
    @Test
    void testToString() {
        OrderService service = new OrderService(orderRepository, productService, clientRepository);
        String str = service.toString();
        assertNotNull(str);
        assertTrue(str.contains("pedidoRepository"));
        assertTrue(str.contains("productService"));
        assertTrue(str.contains("clienteRepository"));
    }

    

    @Test
    void testCriar() {
        OrderRequestDTO request = new OrderRequestDTO();
        List<OrderRequestDTO.ItemDTO> itens = new ArrayList<>();
        OrderRequestDTO.ItemDTO itemDTO = new OrderRequestDTO.ItemDTO();
        itemDTO.setProdutoId(produto.getId());
        itemDTO.setQuantidade(3);
        itens.add(itemDTO);
        request.setItens(itens);

        when(clientRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(productService.buscarPorId(produto.getId())).thenReturn(produto);
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponseDTO response = orderService.create(request, cliente.getId());

        assertNotNull(response);
        assertEquals(cliente.getId(), response.getClienteId());
        assertEquals(1, response.getItens().size());
        assertEquals(produto.getId(), response.getItens().get(0).getProdutoId());
        assertEquals(0, response.getTotal().compareTo(new BigDecimal("30.00")));
    }

    @Test
    void testBuscarPorId() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.valueOf(10.00));
        pedido.setItens(new ArrayList<>());

        when(orderRepository.findByIdAndClienteId(pedido.getId(), cliente.getId())).thenReturn(Optional.of(pedido));

        OrderResponseDTO dto = orderService.searchById(pedido.getId(), cliente.getId());

        assertEquals(cliente.getId(), dto.getClienteId());
    }

    @Test
    void testCancelar() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(cliente);
        pedido.setItens(new ArrayList<>());

        when(orderRepository.findByIdAndClienteId(pedido.getId(), cliente.getId())).thenReturn(Optional.of(pedido));

        orderService.cancel(pedido.getId(), cliente.getId());

        assertTrue(pedido.isCancelado());
        verify(orderRepository).save(pedido);
    }
    @Test
    void testSetPedidoRepository() {
        OrderRepository newRepository = mock(OrderRepository.class);
        orderService.setOrderRepository(newRepository);
        assertEquals(newRepository, orderService.getOrderRepository());
    }

    @Test
    void testSetProductService() {
        ProductService newService = mock(ProductService.class);
        orderService.setProductService(newService);
        assertEquals(newService, orderService.getProductService());
    }

    @Test
    void testSetClienteRepository() {
        ClientRepository newRepository = mock(ClientRepository.class);
        orderService.setClientRepository(newRepository);
        assertEquals(newRepository, orderService.getClientRepository());
    }

    @Test
    void testCanEqual() {
        OrderService service = new OrderService(orderRepository, productService, clientRepository);
        assertTrue(service.canEqual(new OrderService(orderRepository, productService, clientRepository)));
        assertFalse(service.canEqual(new Object()));
    }
    public boolean canEqual(Object other) {
        return other instanceof OrderService;
    }
    @Test
    void testCriar_ClienteNaoEncontrado() {
        OrderRequestDTO request = new OrderRequestDTO();
        List<OrderRequestDTO.ItemDTO> itens = new ArrayList<>();
        OrderRequestDTO.ItemDTO itemDTO = new OrderRequestDTO.ItemDTO();
        itemDTO.setProdutoId(produto.getId());
        itemDTO.setQuantidade(3);
        itens.add(itemDTO);
        request.setItens(itens);

        // Simula que o cliente não foi encontrado
        when(clientRepository.findById(cliente.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.create(request, cliente.getId());
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
    }

    @Test
    void testBuscarPorId_PedidoNaoEncontrado() {
        // Simula que o pedido não foi encontrado
        when(orderRepository.findByIdAndClienteId(999L, cliente.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.searchById(999L, cliente.getId());
        });

        assertEquals("Pedido não encontrado ou acesso negado", exception.getMessage());
    }


}