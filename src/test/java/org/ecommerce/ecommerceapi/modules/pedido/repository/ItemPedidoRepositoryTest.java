package org.ecommerce.ecommerceapi.modules.pedido.repository;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.ecommerce.ecommerceapi.modules.pedido.entity.ItemPedido;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.product.entities.Product;
import org.ecommerce.ecommerceapi.modules.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemPedidoRepositoryTest {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductRepository productRepository;

    private ClienteEntity cliente;
    private Pedido pedido;
    private Product produto;

    @BeforeEach
    void setup() {
        // Criar e salvar Cliente
        cliente = new ClienteEntity();
        cliente.setNome("Cliente Teste");
        cliente.setUsername("clienteTeste");
        cliente.setEmail("cliente@teste.com");
        cliente.setSenha("senha123");
        cliente = clienteRepository.save(cliente);

        // Criar e salvar Pedido vinculado ao Cliente
        pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setCancelado(false);
        pedido.setDateCreate(LocalDateTime.now());
        pedido.setTotal(BigDecimal.ZERO);
        pedido = pedidoRepository.save(pedido);

        // Criar e salvar Produto
        produto = new Product();
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do produto teste");
        produto.setPreco(BigDecimal.valueOf(20.0));
        produto.setEstoque(50);
        produto = productRepository.save(produto);
    }

    @Test
    void testSaveAndFindById() {
        // Criar ItemPedido com referencia ao pedido e produto
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setNomeProduto(produto.getNome());
        itemPedido.setQuantidade(3);
        itemPedido.setPrecoUnitario(produto.getPreco());
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);

        // Salvar ItemPedido
        ItemPedido saved = itemPedidoRepository.save(itemPedido);
        assertNotNull(saved.getId());

        // Buscar pelo ID e validar
        Optional<ItemPedido> found = itemPedidoRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(produto.getNome(), found.get().getNomeProduto());
        assertEquals(3, found.get().getQuantidade());
        assertEquals(pedido.getId(), found.get().getPedido().getId());
        assertEquals(produto.getId(), found.get().getProduto().getId());
    }
}
