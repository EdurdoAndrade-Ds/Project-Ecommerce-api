package org.ecommerce.ecommerceapi.auth.service;

import org.ecommerce.ecommerceapi.auth.dto.AuthRequestDTO;
import org.ecommerce.ecommerceapi.auth.dto.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public AuthResponseDTO authenticate(AuthRequestDTO dto) {
        Authentication auth = new UsernamePasswordAuthenticationToken(dto.email, dto.password);
        authenticationManager.authenticate(auth);
        String token = tokenService.generateToken(dto.email);
        return new AuthResponseDTO(token);
    }
}
