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

    @Test
    void deveCompararObjetosIguais() {
        DeleteClienteDTO dto1 = new DeleteClienteDTO("senha123");
        DeleteClienteDTO dto2 = new DeleteClienteDTO("senha123");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void deveGerarToStringComCampos() {
        DeleteClienteDTO dto = new DeleteClienteDTO("senhaParaExcluir");

        String texto = dto.toString();
        assertTrue(texto.contains("senhaParaExcluir"));
    }

    @Test
    void deveUsarBuilderCorretamente() {
        DeleteClienteDTO dto = DeleteClienteDTO.builder()
                .senha("senhaComBuilder")
                .build();

        assertEquals("senhaComBuilder", dto.getSenha());
    }

    @Test
    void deveVerificarCanEqual() {
        DeleteClienteDTO dto = new DeleteClienteDTO("senha123");
        assertTrue(dto.canEqual(new DeleteClienteDTO("senha456")));
        assertFalse(dto.canEqual(new Object())); // Testa com um objeto de tipo diferente
    }
}
