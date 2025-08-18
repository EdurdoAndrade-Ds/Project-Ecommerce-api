package org.ecommerce.ecommerceapi.modules.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ecommerce.ecommerceapi.modules.order.dto.OrderRequestDTO;
import org.ecommerce.ecommerceapi.modules.order.dto.OrderResponseDTO;
import org.ecommerce.ecommerceapi.modules.order.service.OrderService;
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
public class OrderController {

    @Autowired
    private OrderService orderService;

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
    public ResponseEntity<OrderResponseDTO> create(
            @RequestBody OrderRequestDTO dto,
            Authentication authentication
    ) {
        Long clienteId = Long.parseLong(authentication.getName());
        return new ResponseEntity<>(orderService.create(dto, clienteId), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(
            summary = "Lista todos os pedidos do cliente autenticado",
            description = "Retorna todos os pedidos do cliente autenticado. O ID do cliente é obtido automaticamente da autenticação.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    public ResponseEntity<List<OrderResponseDTO>> listOrder(Authentication authentication) {
        Long clienteId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.listByClient(clienteId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(
            summary = "Busca um pedido por ID",
            description = "Busca um pedido específico do cliente autenticado. O ID do cliente é obtido automaticamente da autenticação.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    public ResponseEntity<OrderResponseDTO> findById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        Long clienteId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.searchById(id, clienteId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso"),
    })
    @Operation(
            summary = "Cancela um pedido",
            description = "Cancela um pedido específico do cliente autenticado. O ID do cliente é obtido automaticamente da autenticação.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    public ResponseEntity<Void> cancel(
            @PathVariable Long id,
            Authentication authentication
    ) {
        Long clienteId = Long.parseLong(authentication.getName());
        orderService.cancel(id, clienteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/historico")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(
            summary = "Histórico de pedidos cancelados",
            description = "Retorna o histórico de pedidos cancelados do cliente autenticado. O ID do cliente é obtido automaticamente da autenticação.",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    public ResponseEntity<List<OrderResponseDTO>> history(Authentication authentication) {
        Long clienteId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.history(clienteId));
    }
}