package org.ecommerce.ecommerceapi.modules.client.controllers;

import org.ecommerce.ecommerceapi.modules.client.dto.AuthClientDTO;
import org.ecommerce.ecommerceapi.modules.client.useCases.AuthClientUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthClientController {

    private final AuthClientUseCase authClientUseCase;

    @Autowired
    public AuthClientController(AuthClientUseCase authClientUseCase) {
        this.authClientUseCase = authClientUseCase;
    }

    @PostMapping("/client")
    public ResponseEntity<Object> auth(@Valid @RequestBody AuthClientDTO authClientDTO) {
        var result = this.authClientUseCase.execute(authClientDTO);
        return ResponseEntity.ok().body(result);
    }

}
