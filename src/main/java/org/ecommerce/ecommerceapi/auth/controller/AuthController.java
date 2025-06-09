package org.ecommerce.ecommerceapi.auth.controller;

import org.ecommerce.ecommerceapi.auth.dto.AuthRequestDTO;
import org.ecommerce.ecommerceapi.auth.dto.AuthResponseDTO;
import org.ecommerce.ecommerceapi.auth.service.AuthService;
import org.ecommerce.ecommerceapi.client.service.ClientService;
import org.ecommerce.ecommerceapi.client.dto.ClientRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ClientService clientService; // <- Corrigir aqui

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO dto) {
        return authService.authenticate(dto);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody ClientRequestDTO dto) {
        clientService.criarCliente(dto); // <- Corrigir aqui
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");
    }
}

