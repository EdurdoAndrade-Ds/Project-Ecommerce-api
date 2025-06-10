package org.ecommerce.ecommerceapi.modules.cliente.useCases;

import org.ecommerce.ecommerceapi.exceptions.AuthenticationException;
import org.ecommerce.ecommerceapi.modules.cliente.dto.AuthClienteDTO;
import org.ecommerce.ecommerceapi.modules.cliente.dto.AuthClienteResponseDTO;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.ecommerce.ecommerceapi.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class AuthClienteUseCase {

    @Autowired ClienteRepository clienteRepository;

    @Autowired PasswordEncoder passwordEncoder;

    @Autowired JWTProvider jwtProvider;

    public AuthClienteResponseDTO execute(AuthClienteDTO authClienteDTO) {
        var cliente = this.clienteRepository.findByUsernameOrEmail(authClienteDTO.getUsername(), authClienteDTO.getUsername())
                .orElseThrow(() -> {
                    throw new AuthenticationException();
                });

        boolean passwordMatches = this.passwordEncoder.matches(authClienteDTO.getSenha(), cliente.getSenha());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        if (!cliente.isAtivo()) {
            throw new AuthenticationException("Cliente desativado");
        }

        var token = jwtProvider.generateToken(cliente.getId().toString(), Arrays.asList("CLIENTE"));

        return AuthClienteResponseDTO.builder()
                .token(token)
                .id(cliente.getId())
                .username(cliente.getUsername())
                .build();
    }
}

