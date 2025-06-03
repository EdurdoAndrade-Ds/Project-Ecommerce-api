package org.ecommerce.ecommerceapi.product.controller;

import org.ecommerce.ecommerceapi.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
@Tag(name = "Produtos", description = "Endpoints de gerenciamento de produtos")
@RestController
@RequestMapping("/produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Cadastrar produto
    @Operation(summary = "Cria um novo produto")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO produtoDTO) {
        ProductResponseDTO createdProduct = productService.criar(produtoDTO);
        return ResponseEntity.status(201).body(createdProduct);
    }

    // Listar todos os produtos
    @Operation(summary = "Lista todos os produtos")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> list() {
        return ResponseEntity.ok(productService.buscarTodos());
    }

    // Buscar produto por ID
    @Operation(summary = "Busca um produto pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar produto
    @Operation(summary = "Atualiza um produto")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO produtoDTO) {
        try {
            return ResponseEntity.ok(productService.atualizar(id, produtoDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar produto
    @Operation(summary = "Remove um produto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            productService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
