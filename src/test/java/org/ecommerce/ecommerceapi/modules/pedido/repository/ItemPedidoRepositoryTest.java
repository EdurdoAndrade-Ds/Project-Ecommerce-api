package org.ecommerce.ecommerceapi.modules.pedido.repository;

import org.ecommerce.ecommerceapi.modules.pedido.entity.ItemPedido;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.product.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ItemPedidoRepositoryTest {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Test
    @DisplayName("Deve salvar e recuperar um ItemPedido com sucesso")
    void testSaveAndFindItemPedido() {
        // Criar produto (sem salvar no banco, apenas para vincular no item)
        Product produto = new Product();
        produto.setId(1L);
        produto.setNome("Produto Teste");

        // Criar pedido (sem status, já que não existe)
        Pedido pedido = new Pedido();
        pedido.setTotal(BigDecimal.valueOf(39.98));
        pedido.setDateCreate(java.time.LocalDateTime.now());

        // Criar ItemPedido
        ItemPedido item = new ItemPedido();
        item.setNomeProduto("Produto Teste");
        item.setQuantidade(2);
        item.setPrecoUnitario(BigDecimal.valueOf(19.99));
        item.setProduto(produto);
        item.setPedido(pedido);

        // Salvar
        ItemPedido saved = itemPedidoRepository.save(item);

        // Verificar
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getNomeProduto()).isEqualTo("Produto Teste");
        assertThat(saved.getQuantidade()).isEqualTo(2);
        assertThat(saved.getPrecoUnitario()).isEqualByComparingTo("19.99");
        assertThat(saved.getProduto()).isEqualTo(produto);
        assertThat(saved.getPedido()).isEqualTo(pedido);
    }
}
