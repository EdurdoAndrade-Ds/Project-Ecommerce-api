package org.ecommerce.ecommerceapi.modules.pedido.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

public class PedidoRepositoryTest {

    @Test
    void testRepositoryMock() {
        PedidoRepository repo = Mockito.mock(PedidoRepository.class);
        assertNotNull(repo);
    }
}
