package org.ecommerce.ecommerceapi.modules.product.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private static Validator validator;

    @BeforeAll
    public static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveCriarProdutoValido() {
        Product produto = new Product();
        produto.setId(1L);
        produto.setNome("Cadeira Gamer");
        produto.setDescricao("Confortável e ergonômica");
        produto.setPreco(BigDecimal.valueOf(999.99));
        produto.setEstoque(50);
        produto.setItens(List.of());

        assertEquals(1L, produto.getId());
        assertEquals("Cadeira Gamer", produto.getNome());
        assertEquals("Confortável e ergonômica", produto.getDescricao());
        assertEquals(BigDecimal.valueOf(999.99), produto.getPreco());
        assertEquals(50, produto.getEstoque());
        assertNotNull(produto.getItens());
    }

    @Test
    void deveDetectarViolacoesDeValidacao() {
        Product produto = new Product();
        produto.setPreco(BigDecimal.valueOf(-10));
        produto.setEstoque(-5);

        Set<ConstraintViolation<Product>> violacoes = validator.validate(produto);
        assertTrue(violacoes.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
        assertTrue(violacoes.stream().anyMatch(v -> v.getPropertyPath().toString().equals("preco")));
        assertTrue(violacoes.stream().anyMatch(v -> v.getPropertyPath().toString().equals("estoque")));
    }

    @Test
    void deveTestarEqualsHashCodeToString() {
        Product p1 = new Product(1L, "Teclado", "Mecânico", BigDecimal.valueOf(200), 10, List.of());
        Product p2 = new Product(1L, "Teclado", "Mecânico", BigDecimal.valueOf(200), 10, List.of());

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertTrue(p1.toString().contains("Teclado"));
    }
}