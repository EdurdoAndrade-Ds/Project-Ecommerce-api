package org.ecommerce.ecommerceapi.product.controller;

import org.ecommerce.ecommerceapi.product.model.Product;
import org.ecommerce.ecommerceapi.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    // cadastrar produto
    @Operation(summary = "Cria um novo produto")
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.salvar(produto));
    }

    // listar todos os produtos
    @Operation(summary = "Lista todos os produtos")
    @GetMapping
    public ResponseEntity<List<Product>> list() {
        return ResponseEntity.ok(productService.buscarTodos());
    }

    // buscar produto por ID
    @Operation(summary = "Busca um produto pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Product> searchForIdProduto(@PathVariable Long id) {
        return productService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // atualizar produto
    @Operation(summary = "Atualiza um produto")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduto(@PathVariable Long id, @Valid @RequestBody Product product) {
        if (!productService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        return ResponseEntity.ok(productService.atualizar(product));
    }

    // remover um produto
    @Operation(summary = "Remove um produto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        if (!productService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
