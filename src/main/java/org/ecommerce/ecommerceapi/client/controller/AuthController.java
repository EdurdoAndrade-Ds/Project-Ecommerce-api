package org.ecommerce.ecommerceapi.client.controller;

import org.ecommerce.ecommerceapi.client.dto.ClientRequestDTO;
import org.ecommerce.ecommerceapi.client.dto.LoginDTO;
import org.ecommerce.ecommerceapi.client.dto.LoginResponseDTO;
import org.ecommerce.ecommerceapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.ecommerce.ecommerceapi.client.service.ClientService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ClientService clientService; // Certifique-se de ter esse service

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());
            authenticationManager.authenticate(authToken);
            String token = jwtUtil.generateToken(dto.getEmail());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ClientRequestDTO dto) {
        try {
            clientService.saveClient(dto);
            return ResponseEntity.ok("Cliente registrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar cliente: " + e.getMessage());
        }
    }
}
