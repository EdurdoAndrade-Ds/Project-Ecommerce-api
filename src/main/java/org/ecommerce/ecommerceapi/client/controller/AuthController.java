package org.ecommerce.ecommerceapi.client.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ecommerce.ecommerceapi.client.model.Client;
import org.ecommerce.ecommerceapi.client.repository.ClientRepository;
import org.ecommerce.ecommerceapi.client.dto.LoginDTO;
import org.ecommerce.ecommerceapi.client.dto.LoginResponseDTO;
import org.ecommerce.ecommerceapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Clientes", description = "Endpoints para autenticação de clientes")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Realiza login do cliente")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Autenticação realizada com sucesso",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Resposta de Sucesso",
                    value = """
                    {
                        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                        "tipo": "Bearer"
                    }
                    """
                )
            )
        ),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());
        authenticationManager.authenticate(authToken);
        String token = jwtUtil.generateToken(dto.getEmail());
        return new LoginResponseDTO(token);
    }

    @Operation(summary = "Registra um novo cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno ao salvar cliente")
    })
    @PostMapping("/register")
    public void register(@RequestBody Client client) {
        client.setSenha(passwordEncoder.encode(client.getSenha()));
        clientRepository.save(client);
    }
}
