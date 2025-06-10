package org.ecommerce.ecommerceapi.modules.pagamento.controller;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.ecommerce.ecommerceapi.modules.pagamento.dto.PagamentoRequestDTO;
import org.ecommerce.ecommerceapi.modules.pagamento.dto.PagamentoResponseDTO;
import org.ecommerce.ecommerceapi.modules.pagamento.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> criar(@RequestBody PagamentoRequestDTO dto) {
        return new ResponseEntity<>(pagamentoService.criar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<PagamentoResponseDTO>> listar(Authentication authentication) {
        Long clienteId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(pagamentoService.listarPorCliente(clienteId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PagamentoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody String novoStatus,
            Authentication authentication
    ) {
        Long clienteId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(pagamentoService.atualizarStatus(id, novoStatus, clienteId));
    }
}