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
public class ItemPedidoRepositoryTest {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductRepository productRepository;

    private Pedido pedido;
    private Product produto;

    @BeforeEach
    void setup() {
        // Cria e salva cliente
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome("Cliente Teste");
        cliente.setUsername("cliente1");
        cliente.setEmail("cliente1@test.com");
        cliente.setSenha("senha123");
        cliente = clienteRepository.save(cliente);

        // Cria e salva pedido com cliente
        pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setCancelado(false);
        pedido.setDateCreate(LocalDateTime.now());
        pedido.setTotal(BigDecimal.valueOf(100.00));
        pedido = pedidoRepository.save(pedido);

        // Cria e salva produto
        produto = new Product();
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição");
        produto.setPreco(BigDecimal.valueOf(10.00));
        produto.setEstoque(100);
        produto = productRepository.save(produto);
    }

    @Test
    void testSaveAndFind() {
        // Cria o itemPedido com referencias salvas
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setNomeProduto(produto.getNome());
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoUnitario(produto.getPreco());
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);

        // Salva e verifica
        itemPedido = itemPedidoRepository.save(itemPedido);

        Optional<ItemPedido> encontrado = itemPedidoRepository.findById(itemPedido.getId());
        assertTrue(encontrado.isPresent());
        assertEquals(itemPedido.getNomeProduto(), encontrado.get().getNomeProduto());
        assertEquals(itemPedido.getPedido().getId(), pedido.getId());
    }
}
