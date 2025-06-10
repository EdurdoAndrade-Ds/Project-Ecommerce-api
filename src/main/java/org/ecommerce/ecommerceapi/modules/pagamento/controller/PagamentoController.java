package org.ecommerce.ecommerceapi.modules.pagamento.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(
            summary = "Cria um novo pagamento",
            description = "Registra um novo pagamento relacionado a um pedido",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> criar(@RequestBody PagamentoRequestDTO dto) {
        return new ResponseEntity<>(pagamentoService.criar(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Lista os pagamentos do cliente autenticado",
            description = "Retorna todos os pagamentos relacionados ao cliente logado",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @GetMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<PagamentoResponseDTO>> listar(Authentication authentication) {
        Long clienteId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(pagamentoService.listarPorCliente(clienteId));
    }

    @Operation(
            summary = "Atualiza o status de um pagamento",
            description = "Permite atualizar o status de um pagamento específico",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
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