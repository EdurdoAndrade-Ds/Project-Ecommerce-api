package org.ecommerce.ecommerceapi.modules.product.controller;

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
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ProductResponseDTO> criar(@RequestBody ProductRequestDTO dto) {
        return new ResponseEntity<>(productService.criar(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.buscarPorIdDTO(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listar() {
        return ResponseEntity.ok(productService.listar());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        productService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ProductResponseDTO> atualizar(@PathVariable Long id, @RequestBody ProductUpdateDTO dto) {
        return ResponseEntity.ok(productService.atualizar(id, dto));
    }

    @PutMapping("/{id}/estoque")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Void> atualizarEstoque(@PathVariable Long id,
                                                @RequestBody ProductStockUpdateRequestDTO dto) {
        productService.atualizarEstoque(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/desconto")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ProductResponseDTO> aplicarDesconto(@PathVariable Long id, @RequestParam Double discountPercentage) {
        return ResponseEntity.ok(productService.aplicarDesconto(id, discountPercentage));
    }
}
