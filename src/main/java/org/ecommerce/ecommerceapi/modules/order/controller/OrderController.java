package org.ecommerce.ecommerceapi.modules.order.controller;

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
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<OrderResponseDTO> create(
            @RequestBody OrderRequestDTO dto,
            Authentication authentication
    ) {
        Long clienteId = Long.parseLong(authentication.getName());
        return new ResponseEntity<>(orderService.create(dto, clienteId), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<OrderResponseDTO>> listOrder(Authentication authentication) {
        Long clienteId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.listByClient(clienteId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<OrderResponseDTO> findById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        Long clienteId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.searchById(id, clienteId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
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
    public ResponseEntity<List<OrderResponseDTO>> history(Authentication authentication) {
        Long clienteId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.history(clienteId));
    }
}
