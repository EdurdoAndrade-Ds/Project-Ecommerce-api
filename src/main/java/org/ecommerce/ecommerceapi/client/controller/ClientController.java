package org.ecommerce.ecommerceapi.client.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.ecommerce.ecommerceapi.client.model.Client;
import org.ecommerce.ecommerceapi.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Endpoints de gerenciamento de clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(summary = "Cria um novo cliente")
    @PostMapping
    public ResponseEntity<Client> create(@Valid @RequestBody Client cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.saveClient(cliente));
    }

    @Operation(summary = "Lista todos os clientes")
    @GetMapping
    public ResponseEntity<List<Client>> list() {
        return ResponseEntity.ok(clientService.listAllClient());
    }

    @Operation(summary = "Busca um cliente pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Client> searchForIdClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.searchForIdClient(id));
    }

    @Operation(summary = "Atualiza um cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @Valid @RequestBody Client client) {
        return ResponseEntity.ok(clientService.updateClient(id, client));
    }

    @Operation(summary = "Remove um cliente pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca clientes por email")
    @GetMapping("/filtro")
    public ResponseEntity<List<Client>> searchForEmail(@RequestParam String email) {
        return ResponseEntity.ok(clientService.searchForEmail(email));
    }

    
}
