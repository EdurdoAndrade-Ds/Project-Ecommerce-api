package org.ecommerce.ecommerceapi.modules.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductStockUpdateRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductUpdateDTO;
import org.ecommerce.ecommerceapi.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    
    @GetMapping("/{id}")
    @Operation(summary = "Busca produto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProductResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.buscarPorIdDTO(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos")
    public ResponseEntity<List<ProductResponseDTO>> listar() {
        return ResponseEntity.ok(productService.listar());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um produto pelo ID")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        productService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um produto existente (sem alterar o estoque)")
    public ResponseEntity<ProductResponseDTO> atualizar(@PathVariable Long id, @RequestBody ProductUpdateDTO dto) {
        return ResponseEntity.ok(productService.atualizar(id, dto));
    }

    @PutMapping("/{id}/estoque")
    @Operation(summary = "Almentar e reduzir o estoque do produto")
    public ResponseEntity<Void> atualizarEstoque(@PathVariable Long id,
                                                @RequestBody ProductStockUpdateRequestDTO dto) {
        productService.atualizarEstoque(id, dto);
        return ResponseEntity.noContent().build();
    }


}