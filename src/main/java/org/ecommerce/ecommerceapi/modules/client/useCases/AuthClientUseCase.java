package org.ecommerce.ecommerceapi.modules.client.useCases;

import org.ecommerce.ecommerceapi.exceptions.AuthenticationException;
import org.ecommerce.ecommerceapi.modules.client.dto.AuthClientDTO;
import org.ecommerce.ecommerceapi.modules.client.dto.AuthClientResponseDTO;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.ecommerce.ecommerceapi.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class AuthClientUseCase {

    @Autowired
    ClientRepository clientRepository;

    @Autowired PasswordEncoder passwordEncoder;

    @Autowired JWTProvider jwtProvider;

    public AuthClientResponseDTO execute(AuthClientDTO authClientDTO) {
        var client = this.clientRepository.findByUsernameOrEmail(authClientDTO.getUsername(), authClientDTO.getUsername())
                .orElseThrow(() -> {
                    throw new AuthenticationException();
                });

        boolean passwordMatches = this.passwordEncoder.matches(authClientDTO.getPassword(), client.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        if (!client.isActive()) {
            throw new AuthenticationException("Cliente desativado");
        }

        var token = jwtProvider.generateToken(client.getId().toString(), Arrays.asList("CLIENTE"));

        return AuthClientResponseDTO.builder()
                .token(token)
                .id(client.getId())
                .username(client.getUsername())
                .build();
    }
}

