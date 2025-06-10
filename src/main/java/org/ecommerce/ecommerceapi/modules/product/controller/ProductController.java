package org.ecommerce.ecommerceapi.modules.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Operation(summary = "Cria um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
    })
    public ResponseEntity<ProductResponseDTO> criar(@RequestBody ProductRequestDTO dto) {
        return new ResponseEntity<>(productService.criar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Lista todos os produtos")
    public ResponseEntity<List<ProductResponseDTO>> listar() {
        return ResponseEntity.ok(productService.listar());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um produto pelo ID")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        productService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}