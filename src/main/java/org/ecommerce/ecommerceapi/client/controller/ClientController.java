package org.ecommerce.ecommerceapi.client.controller;

import java.util.List;
import org.ecommerce.ecommerceapi.client.model.Client;
import org.ecommerce.ecommerceapi.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> create(@Valid @RequestBody Client cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.saveClient(cliente));
    }

    @GetMapping
    public ResponseEntity<List<Client>> list() {
        return ResponseEntity.ok(clientService.listAllClient());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> searchForIdClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.searchForIdClient(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @Valid @RequestBody Client client) {
        return ResponseEntity.ok(clientService.updateClient(id, client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<Client>> searchForEmail(@RequestParam String email) {
        return ResponseEntity.ok(clientService.searchForEmail(email));
    }

    public void teste() {

    }
}