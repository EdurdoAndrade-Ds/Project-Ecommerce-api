package org.ecommerce.ecommerceapi.modules.cliente.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeleteClienteDTOTest {

    @Test
    void deveCriarEDefinirSenha() {
        DeleteClienteDTO dto = new DeleteClienteDTO();
        dto.setSenha("senhaParaExcluir");

        assertEquals("senhaParaExcluir", dto.getSenha());
    }
}