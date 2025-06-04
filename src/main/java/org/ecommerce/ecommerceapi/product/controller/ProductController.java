package org.ecommerce.ecommerceapi.product.controller;

import org.ecommerce.ecommerceapi.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Produto", description = "Endpoints de gerenciamento de produtos")
@RestController
@RequestMapping("/api/produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Criar produto
    @Operation(summary = "Cria um novo produto")
    @PostMapping
    public ProductResponseDTO criar(@RequestBody ProductRequestDTO dto) {
        return productService.criar(dto);
    }

    // Atualizar produto
    @Operation(summary = "Atualizar produto")
    @PutMapping("/{id}")
    public ProductResponseDTO atualizar(@PathVariable Long id, @RequestBody ProductRequestDTO dto) {
        return productService.atualizar(id, dto);
    }

    // Buscar todos os produtos
    @Operation(summary = "Buscar todos os produtos")
    @GetMapping
    public List<ProductResponseDTO> buscarTodos() {
        return productService.buscarTodos();
    }

    // Buscar produto por ID
    @Operation(summary = "Buscar produto por ID")
    @GetMapping("/{id}")
    public ProductResponseDTO buscarPorId(@PathVariable Long id) {
        return productService.buscarPorId(id);
    }

    // Deletar produto
    @Operation(summary = "Deletar produto")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        productService.deletar(id);
    }

    // Verificar disponibilidade de estoque
    @Operation(summary = "Verificar disponibilidade de estoque")
    @GetMapping("/{id}/disponivel")
    public boolean verificarEstoque(@PathVariable Long id, @RequestParam int quantidade) {
        return productService.verificarEstoque(id, quantidade);
    }

    // Adicionar ao estoque
    @Operation(summary = "Adicionar ao estoque")
    @PutMapping("/{id}/estoque/adicionar")
    public ResponseEntity<Void> adicionarEstoque(@PathVariable Long id, @RequestParam int quantidade) {
        productService.adicionarAoEstoque(id, quantidade);
        return ResponseEntity.noContent().build();
    }

    // Reduzir estoque
    @Operation(summary = "Reduzir estoque")
    @PutMapping("/{id}/reduzir-estoque")
    public void atualizarEstoque(@PathVariable Long id, @RequestParam int quantidade) {
        productService.atualizarEstoque(id, quantidade);
    }
}
