package org.ecommerce.ecommerceapi.modules.client.controllers;

import org.ecommerce.ecommerceapi.exceptions.ClientNotFoundException;
import org.ecommerce.ecommerceapi.modules.client.dto.CreateClientDTO;
import org.ecommerce.ecommerceapi.modules.client.dto.DeleteClientDTO;
import org.ecommerce.ecommerceapi.modules.client.dto.UpdateClientDTO;
import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.useCases.CreateClientUseCase;
import org.ecommerce.ecommerceapi.modules.client.useCases.DeleteClientUseCase;
import org.ecommerce.ecommerceapi.modules.client.useCases.UpdateClientUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private CreateClientUseCase createClienteUseCase;
    @Autowired
    private DeleteClientUseCase deleteClienteUseCase;
    @Autowired
    private UpdateClientUseCase updateClienteUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateClientDTO createClientDTO) {

        try {
            var clienteEntity = ClientEntity.builder()
                    .name(createClientDTO.getName())
                    .username(createClientDTO.getUsername())
                    .email(createClientDTO.getEmail())
                    .password(createClientDTO.getPassword())
                    .phone(createClientDTO.getPhone())
                    .address(createClientDTO.getAddress())
                    .city(createClientDTO.getCity())
                    .state(createClientDTO.getState())
                    .cep(createClientDTO.getCep())
                    .build();

            var result = this.createClienteUseCase.execute(clienteEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Object> delete(
            @Valid @RequestBody DeleteClientDTO deleteClientDTO,
            Authentication authentication
    ) {
        try {
            var clienteId = Long.parseLong(authentication.getName());
            this.deleteClienteUseCase.execute(clienteId, deleteClientDTO.getPassword());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Object> update(
            @Valid @RequestBody UpdateClientDTO updateClientDTO,
            Authentication authentication
    ) {
        Long clienteId = Long.parseLong(authentication.getName());

        var updatedClient = updateClienteUseCase.execute(clienteId, updateClientDTO);
        return ResponseEntity.ok(updatedClient);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Object> handleClientNotFound(ClientNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
