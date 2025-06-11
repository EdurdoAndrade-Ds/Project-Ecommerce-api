package org.ecommerce.ecommerceapi.modules.pedido.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;

public class ItemPedidoRepositoryTest {

    @Test
<<<<<<< HEAD
    @DisplayName("Deve salvar e recuperar um ItemPedido com sucesso")
    void testSaveAndFindItemPedido() {
        Product produto = new Product();
        produto.setId(1L);
        produto.setNome("Produto Teste");

        Pedido pedido = new Pedido();
        pedido.setId(1L);

        ItemPedido item = new ItemPedido();
        item.setNomeProduto("Produto Teste");
        item.setQuantidade(2);
        item.setPrecoUnitario(BigDecimal.valueOf(19.99));
        item.setProduto(produto);
        item.setPedido(pedido);

        ItemPedido saved = itemPedidoRepository.save(item);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getNomeProduto()).isEqualTo("Produto Teste");
        assertThat(saved.getQuantidade()).isEqualTo(2);
        assertThat(saved.getPrecoUnitario()).isEqualByComparingTo("19.99");
        assertThat(saved.getProduto()).isEqualTo(produto);
        assertThat(saved.getPedido()).isEqualTo(pedido);
=======
    void testRepositoryMock() {
        ItemPedidoRepository repo = Mockito.mock(ItemPedidoRepository.class);
        assertNotNull(repo);
>>>>>>> ab764e7c142e15083dc06c3d77cc7a4ac77f6659
    }
}
