package org.ecommerce.ecommerceapi.modules.pedido.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoRequestDTO;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoResponseDTO;
import org.ecommerce.ecommerceapi.modules.pedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "API para gerenciamento de pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(
            summary = "Cria um novo pedido",
            description = "Cria um novo pedido para o cliente autenticado. O ID do cliente é obtido automaticamente da autenticação.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PedidoResponseDTO> criar(
            @RequestBody PedidoRequestDTO dto,
            Authentication authentication
    ) {
        Long clienteId = Long.parseLong(authentication.getName());
        return new ResponseEntity<>(pedidoService.criar(dto, clienteId), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(
            summary = "Lista todos os pedidos do cliente autenticado",
            description = "Retorna todos os pedidos do cliente autenticado. O ID do cliente é obtido automaticamente da autenticação.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    public ResponseEntity<List<PedidoResponseDTO>> listar(Authentication authentication) {
        Long clienteId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(pedidoService.listarPorCliente(clienteId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(
            summary = "Busca um pedido por ID",
            description = "Busca um pedido específico do cliente autenticado. O ID do cliente é obtido automaticamente da autenticação.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    public ResponseEntity<PedidoResponseDTO> buscarPorId(
            @PathVariable Long id,
            Authentication authentication
    ) {
        Long clienteId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(pedidoService.buscarPorId(id, clienteId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(
            summary = "Cancela um pedido",
            description = "Cancela um pedido específico do cliente autenticado. O ID do cliente é obtido automaticamente da autenticação.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    public ResponseEntity<Void> cancelar(
            @PathVariable Long id,
            Authentication authentication
    ) {
        Long clienteId = Long.parseLong(authentication.getName());
        pedidoService.cancelar(id, clienteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/historico")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(
            summary = "Histórico de pedidos cancelados",
            description = "Retorna o histórico de pedidos cancelados do cliente autenticado. O ID do cliente é obtido automaticamente da autenticação.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    public ResponseEntity<List<PedidoResponseDTO>> historico(Authentication authentication) {
        Long clienteId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(pedidoService.historico(clienteId));
    }
}
