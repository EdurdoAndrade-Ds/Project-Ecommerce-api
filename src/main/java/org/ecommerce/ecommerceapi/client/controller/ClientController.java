package org.ecommerce.ecommerceapi.client.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.ecommerce.ecommerceapi.client.dto.ClientRequestDTO;
import org.ecommerce.ecommerceapi.client.dto.ClientResponseDTO;
import org.ecommerce.ecommerceapi.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(summary = "Cria um novo cliente")
    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@Valid @RequestBody ClientRequestDTO dto) {
        ClientResponseDTO createdClient = clientService.saveClient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @Operation(summary = "Lista todos os clientes")
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> list() {
        List<ClientResponseDTO> clients = clientService.listAllClient();
        return ResponseEntity.ok(clients);
    }

    @Operation(summary = "Busca um cliente pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> searchForIdClient(@PathVariable Long id) {
        ClientResponseDTO client = clientService.searchForIdClient(id);
        return ResponseEntity.ok(client);
    }

    @Operation(summary = "Atualiza um cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @Valid @RequestBody ClientRequestDTO dto) {
        ClientResponseDTO updatedClient = clientService.updateClient(id, dto);
        return ResponseEntity.ok(updatedClient);
    }

    @Operation(summary = "Remove um cliente pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca clientes por email")
    @GetMapping("/filtro")
    public ResponseEntity<List<ClientResponseDTO>> searchForEmail(@RequestParam String email) {
        List<ClientResponseDTO> clients = clientService.searchForEmail(email);
        return ResponseEntity.ok(clients);
    }
}
