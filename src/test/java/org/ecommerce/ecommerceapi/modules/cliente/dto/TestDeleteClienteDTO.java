package org.ecommerce.ecommerceapi.modules.cliente.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TestDeleteClienteDTOValidation {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testSenhaNotBlank() {
        DeleteClienteDTO dto = new DeleteClienteDTO();
        dto.setSenha(""); // senha vazia, deve falhar

        Set<ConstraintViolation<DeleteClienteDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());

        boolean hasSenhaViolation = violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("senha"));
        assertTrue(hasSenhaViolation, "Deve ter violação no campo senha quando estiver em branco");
    }

    @Test
    void testSenhaValid() {
        DeleteClienteDTO dto = new DeleteClienteDTO();
        dto.setSenha("senhaSegura123"); // senha válida

        Set<ConstraintViolation<DeleteClienteDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Não deve ter violação para senha válida");
    }
}
