package org.ecommerce.ecommerceapi.modules.cliente.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UpdateClienteDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDTO() {
        UpdateClienteDTO dto = UpdateClienteDTO.builder()
                .nome("Carlos")
                .username("carlos123")
                .email("carlos@email.com")
                .telefone("987654321")
                .endereco("Rua B, 456")
                .cidade("Rio de Janeiro")
                .estado("RJ")
                .cep("98765-432")
                .build();

        Set<ConstraintViolation<UpdateClienteDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Não deve haver violações para DTO válido");
    }

    @Test
    void testInvalidUsernameWithSpaces() {
        UpdateClienteDTO dto = UpdateClienteDTO.builder()
                .username("carlos 123")
                .build();

        Set<ConstraintViolation<UpdateClienteDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());

        boolean hasUsernameViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("username"));
        assertTrue(hasUsernameViolation, "Deve haver violação para username com espaço");
    }

    @Test
    void testInvalidEmail() {
        UpdateClienteDTO dto = UpdateClienteDTO.builder()
                .email("email-invalido")
                .build();

        Set<ConstraintViolation<UpdateClienteDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());

        boolean hasEmailViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email"));
        assertTrue(hasEmailViolation, "Deve haver violação para email inválido");
    }
}
