package org.ecommerce.ecommerceapi.modules.pedido.service;

import org.ecommerce.ecommerceapi.EcommerceApiApplication;
import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoRequestDTO;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoResponseDTO;
import org.ecommerce.ecommerceapi.modules.product.entities.Product;
import org.ecommerce.ecommerceapi.modules.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest(classes = EcommerceApiApplication.class)
@Transactional // garante rollback após cada teste
class PedidoServiceIntegrationTest {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductRepository productRepository;

    private ClienteEntity cliente;
    private Product produto;

    @BeforeEach
    void setup() {
        cliente = new ClienteEntity();
        cliente.setNome("Cliente Teste");
        cliente.setUsername("cliente1");
        cliente.setEmail("cliente1@test.com");
        cliente.setSenha("senha123");
        cliente = clienteRepository.save(cliente);

        produto = new Product();
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição");
        produto.setPreco(BigDecimal.valueOf(10.00));
        produto.setEstoque(100);
        produto = productRepository.save(produto);
    }

    @Test
    void criarPedido_Integração() {
        PedidoRequestDTO.ItemDTO itemDTO = new PedidoRequestDTO.ItemDTO();
        itemDTO.setProdutoId(produto.getId());
        itemDTO.setQuantidade(3);

        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setItens(List.of(itemDTO));

        PedidoResponseDTO response = pedidoService.criar(dto, cliente.getId());

        assertNotNull(response);
        assertEquals(cliente.getId(), response.getClienteId());
        assertEquals(1, response.getItens().size());
        assertEquals(produto.getId(), response.getItens().get(0).getProdutoId());
        assertEquals(BigDecimal.valueOf(30.00), response.getTotal());
    }

    @Test
    void listarPedidosPorCliente_Integração() {
        criarPedido_Integração(); // cria um pedido para garantir dados

        List<PedidoResponseDTO> pedidos = pedidoService.listarPorCliente(cliente.getId());
        assertFalse(pedidos.isEmpty());
        assertEquals(cliente.getId(), pedidos.get(0).getClienteId());
    }

    @Test
    void contextLoads() {
        // Testa apenas se o contexto do Spring carrega corretamente
    }
}