package org.ecommerce.ecommerceapi.modules.pedido.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

public class ItemPedidoRepositoryTest {

    @Test
    void testRepositoryMock() {
        ItemPedidoRepository repo = Mockito.mock(ItemPedidoRepository.class);
        assertNotNull(repo);
    }
}
