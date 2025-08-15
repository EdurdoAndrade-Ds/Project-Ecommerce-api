package org.ecommerce.ecommerceapi.modules.cliente.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ClienteEntityTest {

    private ClienteEntity make(Long id, String nome, String email) {
        return ClienteEntity.builder()
                .id(id)
                .nome(nome)
                .email(email)
                .build();
    }

    @Test
    void gettersSetters_ok() {
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
        assertTrue(entity.isAtivo());
    }


    @Test
    void equals_hashCode_byId_fullBranches() {
        ClienteEntity base = make(1L, "Ana", "ana@g.com");

        assertEquals(base, base);

        ClienteEntity sameId = make(1L, "Outro", "outro@g.com");
        assertEquals(base, sameId);
        assertEquals(base.hashCode(), sameId.hashCode());

        assertNotEquals(base, make(2L, "Ana", "ana@a.com"));

        assertNotEquals(make(null, "Ana", "ana@g.com"), make(1L, "Ana", "ana@g.com"));
        assertNotEquals(make(1L, "Ana", "ana@g.com"), make(null, "Ana", "ana@g.com"));

        ClienteEntity t1 = make(null, "Ana", "ana@g.com");
        ClienteEntity t2 = make(null, "Ana","ana@g.com");
        assertNotEquals(t1, t2);

        assertNotEquals(base, null);
        assertNotEquals(base, "String");

        t1.hashCode();
        base.hashCode();
    }

    @Test
    void toString_containsKeyFields() {
        ClienteEntity entity = ClienteEntity.builder()
                .id(1L)
                .nome("Lucas")
                .email("lucas@email.com")
                .build();

        String s = entity.toString();


        assertTrue(s.contains("ClienteEntity"));
        assertTrue(s.contains("id=1"));
        assertTrue(s.contains("nome=Lucas"));
        assertTrue(s.contains("email=lucas@email.com"));
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
