package org.ecommerce.ecommerceapi.client.controller;

import jakarta.validation.Valid;
import org.ecommerce.ecommerceapi.client.dto.ClientCreateDTO;
import org.ecommerce.ecommerceapi.client.dto.ClientRequestDTO;
import org.ecommerce.ecommerceapi.client.dto.ClientResponseDTO;
import org.ecommerce.ecommerceapi.client.model.Client;
import org.ecommerce.ecommerceapi.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> cadastrar(@RequestBody ClientRequestDTO dto) {
        Client client = clientService.cadastrar(dto);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/register")
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody @Valid ClientRequestDTO dto) {
        ClientResponseDTO response = clientService.criarCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

