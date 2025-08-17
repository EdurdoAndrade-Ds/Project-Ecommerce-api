package org.ecommerce.ecommerceapi.modules.client.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ClientEntityTest {

    private ClientEntity make(Long id, String nome, String email) {
        return ClientEntity.builder()
                .id(id)
                .name(nome)
                .email(email)
                .build();
    }

    @Test
    void gettersSetters_ok() {
        ClientEntity entity = new ClientEntity();
        LocalDateTime now = LocalDateTime.now();

        entity.setId(1L);
        entity.setName("Lucas");
        entity.setUsername("lucas123");
        entity.setEmail("lucas@email.com");
        entity.setPassword("senhaSegura");
        entity.setPhone("555555555");
        entity.setAddress("Av. Central, 100");
        entity.setCity("Curitiba");
        entity.setState("PR");
        entity.setCep("80000-000");
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setActive(true);

        assertEquals(1L, entity.getId());
        assertEquals("Lucas", entity.getName());
        assertEquals("lucas123", entity.getUsername());
        assertTrue(entity.isActive());
    }


    @Test
    void equals_hashCode_byId_fullBranches() {
        ClientEntity base = make(1L, "Ana", "ana@g.com");

        assertEquals(base, base);

        ClientEntity sameId = make(1L, "Outro", "outro@g.com");
        assertEquals(base, sameId);
        assertEquals(base.hashCode(), sameId.hashCode());

        assertNotEquals(base, make(2L, "Ana", "ana@a.com"));

        assertNotEquals(make(null, "Ana", "ana@g.com"), make(1L, "Ana", "ana@g.com"));
        assertNotEquals(make(1L, "Ana", "ana@g.com"), make(null, "Ana", "ana@g.com"));

        ClientEntity t1 = make(null, "Ana", "ana@g.com");
        ClientEntity t2 = make(null, "Ana","ana@g.com");
        assertNotEquals(t1, t2);

        assertNotEquals(base, null);
        assertNotEquals(base, "String");

        t1.hashCode();
        base.hashCode();
    }

    @Test
    void toString_containsKeyFields() {
        ClientEntity entity = ClientEntity.builder()
                .id(1L)
                .name("Lucas")
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

        ClientEntity entity = ClientEntity.builder()
                .id(1L)
                .name("Lucas")
                .username("lucas123")
                .email("lucas@email.com")
                .password("senhaSegura")
                .phone("555555555")
                .address("Av. Central, 100")
                .city("Curitiba")
                .state("PR")
                .cep("80000-000")
                .createdAt(now)
                .updatedAt(now)
                .active(true)
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

        ClientEntity entity = ClientEntity.builder()
                .id(1L)
                .password("Lucas")
                .username("lucas123")
                .email("lucas@email.com")
                .password("senhaSegura")
                .phone("555555555")
                .address("Av. Central, 100")
                .city("Curitiba")
                .state("PR")
                .cep("80000-000")
                .createdAt(now)
                .updatedAt(now)
                .active(true)
                .build();

        assertEquals(1L, entity.getId());
        assertEquals("Lucas", entity.getName());
        assertEquals("lucas123", entity.getUsername());
        assertEquals("lucas@email.com", entity.getEmail());
        assertEquals("senhaSegura", entity.getPassword());
        assertEquals("555555555", entity.getPhone());
        assertEquals("Av. Central, 100", entity.getAddress());
        assertEquals("Curitiba", entity.getCity());
        assertEquals("PR", entity.getState());
        assertEquals("80000-000", entity.getCep());
        assertTrue(entity.isActive());
    }
}
