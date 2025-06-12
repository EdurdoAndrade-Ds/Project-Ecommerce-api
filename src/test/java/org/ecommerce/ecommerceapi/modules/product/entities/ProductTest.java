package org.ecommerce.ecommerceapi.modules.product.entities;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarProdutoValido() {
        Product product = new Product(
                1L,
                "Produto Válido",
                "Descrição de teste",
                BigDecimal.valueOf(99.90),
                10,
                null // Lista de itens pode ser nula aqui
        );

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty(), "Produto válido não deveria ter violações");
    }

    @Test
    void deveDetectarNomeEmBranco() {
        Product product = new Product(
                1L,
                "   ",  // nome em branco
                "Descrição de teste",
                BigDecimal.valueOf(99.90),
                10,
                null
        );

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
    }

    @Test
    void deveDetectarPrecoNegativo() {
        Product product = new Product(
                1L,
                "Produto Teste",
                "Descrição",
                BigDecimal.valueOf(-10), // inválido
                5,
                null
        );

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("preco")));
    }

    @Test
    void deveDetectarEstoqueNulo() {
        Product product = new Product(
                1L,
                "Produto Teste",
                "Descrição",
                BigDecimal.valueOf(10),
                null, // estoque nulo
                null
        );

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("estoque")));
    }
}
