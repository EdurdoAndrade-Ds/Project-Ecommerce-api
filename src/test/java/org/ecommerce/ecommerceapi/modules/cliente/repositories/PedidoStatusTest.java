package org.ecommerce.ecommerceapi.modules.pedido.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

public class PedidoStatusTest {

    @Test
    void testRepositoryMock() {
        PedidoStatus repo = Mockito.mock(PedidoStatus.class);
        assertNotNull(repo);
    }
}
