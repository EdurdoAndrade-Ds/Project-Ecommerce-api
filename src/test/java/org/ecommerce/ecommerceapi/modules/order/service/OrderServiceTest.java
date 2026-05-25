package org.ecommerce.ecommerceapi.modules.order.service;

import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.ecommerce.ecommerceapi.modules.order.dto.OrderRequestDTO;
import org.ecommerce.ecommerceapi.modules.order.dto.OrderResponseDTO;
import org.ecommerce.ecommerceapi.modules.order.entity.Order;
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
        produto.setName("Produto Teste");
        produto.setPrice(BigDecimal.valueOf(10.00));
    }

    @Test
    void testListarPorCliente() {
        Order order = new Order();
        order.setId(100L);
        order.setCliente(cliente);
        order.setTotal(BigDecimal.valueOf(150.00));
        order.setItens(new ArrayList<>());
        when(orderRepository.findByClienteId(cliente.getId())).thenReturn(List.of(order));
        List<OrderResponseDTO> responseList = orderService.listByClient(cliente.getId());
        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        OrderResponseDTO dto = responseList.get(0);
        assertEquals(order.getId(), dto.getId());
        assertEquals(cliente.getId(), dto.getClienteId());
        assertEquals(order.getTotal(), dto.getTotal());
    }

    @Test
    void testHistorico() {
        Order orderCancelado = new Order();
        orderCancelado.setId(200L);
        orderCancelado.setCliente(cliente);
        orderCancelado.setTotal(BigDecimal.valueOf(200.00));
        orderCancelado.setCancelado(true);
        orderCancelado.setItens(new ArrayList<>());
        when(orderRepository.findByClienteIdAndCanceladoTrue(cliente.getId())).thenReturn(List.of(orderCancelado));
        List<OrderResponseDTO> historicoList = orderService.history(cliente.getId());
        assertNotNull(historicoList);
        assertEquals(1, historicoList.size());
        OrderResponseDTO dto = historicoList.get(0);
        assertEquals(orderCancelado.getId(), dto.getId());
        assertEquals(cliente.getId(), dto.getClienteId());
        assertEquals(orderCancelado.getTotal(), dto.getTotal());
    }



        @Test
    void testBuscarPorIdDTO_Sucesso() {
        Order order = new Order();
        order.setId(123L);
        order.setCliente(cliente);
        order.setTotal(BigDecimal.valueOf(100));
        order.setDateCreate(LocalDateTime.now());
        order.setItens(new ArrayList<>());

        when(orderRepository.findByIdAndClienteId(123L, 1L)).thenReturn(Optional.of(order));

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
        Order order = new Order();
        order.setId(1L);
        order.setCliente(cliente);
        order.setTotal(BigDecimal.valueOf(10.00));
        order.setItens(new ArrayList<>());

        when(orderRepository.findByIdAndClienteId(order.getId(), cliente.getId())).thenReturn(Optional.of(order));

        OrderResponseDTO dto = orderService.searchById(order.getId(), cliente.getId());

        assertEquals(cliente.getId(), dto.getClienteId());
    }

    @Test
    void testCancelar() {
        Order order = new Order();
        order.setId(1L);
        order.setCliente(cliente);
        order.setItens(new ArrayList<>());

        when(orderRepository.findByIdAndClienteId(order.getId(), cliente.getId())).thenReturn(Optional.of(order));

        orderService.cancel(order.getId(), cliente.getId());

        assertTrue(order.isCancelado());
        verify(orderRepository).save(order);
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