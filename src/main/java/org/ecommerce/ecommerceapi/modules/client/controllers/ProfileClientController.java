package org.ecommerce.ecommerceapi.modules.client.controllers;

import org.ecommerce.ecommerceapi.exceptions.ClientUnauthorizedException;
import org.ecommerce.ecommerceapi.modules.client.useCases.ProfileClientUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cliente")
public class ProfileClientController {

    private final ProfileClientUseCase profileClientUseCase;

    public ProfileClientController(ProfileClientUseCase profileClientUseCase) {
        this.profileClientUseCase = profileClientUseCase;
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Object> profile(HttpServletRequest request) {
            Object idAttr = request.getAttribute("cliente_id");
            if(idAttr == null) {
                throw new ClientUnauthorizedException("Token invalido ou ausente");
            }

            long clienteId = Long.parseLong(idAttr.toString());
            return ResponseEntity.ok(profileClientUseCase.execute(clienteId));
    }

}
