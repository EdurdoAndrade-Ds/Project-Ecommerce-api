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
        // Criando produto e pedido fictícios (mockados)
        Product produto = new Product();
        produto.setId(1L);
        produto.setNome("Produto Teste");

        Pedido pedido = new Pedido();
        pedido.setId(1L); // simulação, pois não estamos persistindo de fato

        // Criando o ItemPedido
        ItemPedido item = new ItemPedido();
        item.setNomeProduto("Produto Teste");
        item.setQuantidade(2);
        item.setPrecoUnitario(BigDecimal.valueOf(19.99));
        item.setProduto(produto);
        item.setPedido(pedido);

        // Salvando no repositório
        ItemPedido saved = itemPedidoRepository.save(item);

        // Verificando persistência
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getNomeProduto()).isEqualTo("Produto Teste");
        assertThat(saved.getQuantidade()).isEqualTo(2);
        assertThat(saved.getPrecoUnitario()).isEqualByComparingTo("19.99");
        assertThat(saved.getProduto()).isEqualTo(produto);
        assertThat(saved.getPedido()).isEqualTo(pedido);
    }
}
