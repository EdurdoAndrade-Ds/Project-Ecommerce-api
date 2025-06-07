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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Clientes", description = "Operações de autenticação e gerenciamento de clientes")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ClientService clientService; // Certifique-se de ter esse service

    @Operation(summary = "Autentica um cliente e gera um token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login bem-sucedido",
            content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
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

    @Operation(summary = "Registra um novo cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro ao registrar cliente")
    })
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
