package org.ecommerce.ecommerceapi.modules.pagamento.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.ecommerce.ecommerceapi.modules.pagamento.dto.PagamentoRequestDTO;
import org.ecommerce.ecommerceapi.modules.pagamento.dto.PagamentoResponseDTO;
import org.ecommerce.ecommerceapi.modules.pagamento.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/pagamentos")
@Tag(name = "Pagamento", description = "API para gerenciamento de pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping
    @Operation(
            summary = "Cria novo pagamento para um pedido",
            security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<PagamentoResponseDTO> criar(@RequestBody @Valid PagamentoRequestDTO dto) {
        return new ResponseEntity<>(pagamentoService.criar(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping
    @Operation(summary = "Listar pagamentos", description = "Lista todos os pagamentos do cliente autenticado")
    public ResponseEntity<List<PagamentoResponseDTO>> listar(Authentication authentication) {
        Long clienteId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(pagamentoService.listar(clienteId));
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento por ID", description = "Busca um pagamento específico pelo ID")
    public ResponseEntity<PagamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagamentoService.buscarPorId(id));
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar status do pagamento", description = "Atualiza o status de um pagamento existente")
    public ResponseEntity<PagamentoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(pagamentoService.atualizarStatus(id, status));
    }
}
