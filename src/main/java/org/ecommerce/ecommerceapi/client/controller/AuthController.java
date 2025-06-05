package org.ecommerce.ecommerceapi.client.controller;

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

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());
        authenticationManager.authenticate(authToken);
        String token = jwtUtil.generateToken(dto.getEmail());
        return new LoginResponseDTO(token);
    }

    @PostMapping("/register")
    public void register(@RequestBody Client client) {
        client.setSenha(passwordEncoder.encode(client.getSenha()));
        clientRepository.save(client);
    }
}
