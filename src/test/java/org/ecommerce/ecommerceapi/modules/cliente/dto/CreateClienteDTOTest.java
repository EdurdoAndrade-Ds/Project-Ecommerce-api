package org.ecommerce.ecommerceapi.modules.cliente.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreateClienteDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidatorInstance() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDTO() {
        CreateClienteDTO dto = CreateClienteDTO.builder()
                .nome("João")
                .username("joaosilva")
                .email("joao@email.com")
                .senha("senha1234")
                .telefone("123456789")
                .endereco("Rua A, 123")
                .cidade("São Paulo")
                .estado("SP")
                .cep("12345-678")
                .build();

        Set<ConstraintViolation<CreateClienteDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Não deve ter violações para DTO válido");
    }

    @Test
    void testInvalidEmail() {
        CreateClienteDTO dto = CreateClienteDTO.builder()
                .nome("João")
                .username("joaosilva")
                .email("email-invalido")
                .senha("senha1234")
                .build();

        Set<ConstraintViolation<CreateClienteDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());

        boolean hasEmailViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email"));
        assertTrue(hasEmailViolation, "Deve ter violação no campo email");
    }

    @Test
    void testBlankNome() {
        CreateClienteDTO dto = CreateClienteDTO.builder()
                .nome("")
                .username("joaosilva")
                .email("joao@email.com")
                .senha("senha1234")
                .build();

        Set<ConstraintViolation<CreateClienteDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());

        boolean hasNomeViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nome"));
        assertTrue(hasNomeViolation, "Deve ter violação no campo nome");
    }

    @Test
    void testUsernameWithSpaces() {
        CreateClienteDTO dto = CreateClienteDTO.builder()
                .nome("João")
                .username("joao silva")
                .email("joao@email.com")
                .senha("senha1234")
                .build();

        Set<ConstraintViolation<CreateClienteDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());

        boolean hasUsernameViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("username"));
        assertTrue(hasUsernameViolation, "Deve ter violação no campo username");
    }

    @Test
    void testSenhaTooShort() {
        CreateClienteDTO dto = CreateClienteDTO.builder()
                .nome("João")
                .username("joaosilva")
                .email("joao@email.com")
                .senha("123")
                .build();

        Set<ConstraintViolation<CreateClienteDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());

        boolean hasSenhaViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("senha"));
        assertTrue(hasSenhaViolation, "Deve ter violação no campo senha");
    }
}
