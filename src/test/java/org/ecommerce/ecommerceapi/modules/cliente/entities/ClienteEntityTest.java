package org.ecommerce.ecommerceapi.modules.cliente.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ClienteEntityTest {

    @Test
    void testGettersAndSetters() {
        ClienteEntity entity = new ClienteEntity();

        LocalDateTime now = LocalDateTime.now();

        entity.setId(1L);
        entity.setNome("Lucas");
        entity.setUsername("lucas123");
        entity.setEmail("lucas@email.com");
        entity.setSenha("senhaSegura");
        entity.setTelefone("555555555");
        entity.setEndereco("Av. Central, 100");
        entity.setCidade("Curitiba");
        entity.setEstado("PR");
        entity.setCep("80000-000");
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setAtivo(true);

        assertEquals(1L, entity.getId());
        assertEquals("Lucas", entity.getNome());
        assertEquals("lucas123", entity.getUsername());
        assertEquals("lucas@email.com", entity.getEmail());
        assertEquals("senhaSegura", entity.getSenha());
        assertEquals("555555555", entity.getTelefone());
        assertEquals("Av. Central, 100", entity.getEndereco());
        assertEquals("Curitiba", entity.getCidade());
        assertEquals("PR", entity.getEstado());
        assertEquals("80000-000", entity.getCep());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
        assertTrue(entity.isAtivo());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();

        ClienteEntity entity1 = ClienteEntity.builder()
                .id(1L)
                .nome("Lucas")
                .username("lucas123")
                .email("lucas@email.com")
                .senha("senhaSegura")
                .telefone("555555555")
                .endereco("Av. Central, 100")
                .cidade("Curitiba")
                .estado("PR")
                .cep("80000-000")
                .createdAt(now)
                .updatedAt(now)
                .ativo(true)
                .build();

        ClienteEntity entity2 = ClienteEntity.builder()
                .id(1L)
                .nome("Lucas")
                .username("lucas123")
                .email("lucas@email.com")
                .senha("senhaSegura")
                .telefone("555555555")
                .endereco("Av. Central, 100")
                .cidade("Curitiba")
                .estado("PR")
                .cep("80000-000")
                .createdAt(now)
                .updatedAt(now)
                .ativo(true)
                .build();

        ClienteEntity entity3 = ClienteEntity.builder()
                .id(2L)
                .nome("Maria")
                .username("maria321")
                .email("maria@email.com")
                .senha("senhaForte")
                .telefone("999999999")
                .endereco("Rua das Laranjeiras, 200")
                .cidade("Florianópolis")
                .estado("SC")
                .cep("88000-000")
                .createdAt(now)
                .updatedAt(now)
                .ativo(false)
                .build();

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
        assertNotEquals(entity1, entity3);
    }

    @Test
    void testToString() {
        LocalDateTime now = LocalDateTime.now();

        ClienteEntity entity = ClienteEntity.builder()
                .id(1L)
                .nome("Lucas")
                .username("lucas123")
                .email("lucas@email.com")
                .senha("senhaSegura")
                .telefone("555555555")
                .endereco("Av. Central, 100")
                .cidade("Curitiba")
                .estado("PR")
                .cep("80000-000")
                .createdAt(now)
                .updatedAt(now)
                .ativo(true)
                .build();

        String str = entity.toString();
        assertTrue(str.contains("id=1"));
        assertTrue(str.contains("nome=Lucas"));
        assertTrue(str.contains("username=lucas123"));
        assertTrue(str.contains("email=lucas@email.com"));
        assertTrue(str.contains("senha=senhaSegura"));
        assertTrue(str.contains("telefone=555555555"));
        assertTrue(str.contains("endereco=Av. Central, 100"));
        assertTrue(str.contains("cidade=Curitiba"));
        assertTrue(str.contains("estado=PR"));
        assertTrue(str.contains("cep=80000-000"));
        assertTrue(str.contains("ativo=true"));
    }

    @Test
    void testBuilder() {
        LocalDateTime now = LocalDateTime.now();

        ClienteEntity entity = ClienteEntity.builder()
                .id(1L)
                .nome("Lucas")
                .username("lucas123")
                .email("lucas@email.com")
                .senha("senhaSegura")
                .telefone("555555555")
                .endereco("Av. Central, 100")
                .cidade("Curitiba")
                .estado("PR")
                .cep("80000-000")
                .createdAt(now)
                .updatedAt(now)
                .ativo(true)
                .build();

        assertEquals(1L, entity.getId());
        assertEquals("Lucas", entity.getNome());
        assertEquals("lucas123", entity.getUsername());
        assertEquals("lucas@email.com", entity.getEmail());
        assertEquals("senhaSegura", entity.getSenha());
        assertEquals("555555555", entity.getTelefone());
        assertEquals("Av. Central, 100", entity.getEndereco());
        assertEquals("Curitiba", entity.getCidade());
        assertEquals("PR", entity.getEstado());
        assertEquals("80000-000", entity.getCep());
        assertTrue(entity.isAtivo());
    }
}
