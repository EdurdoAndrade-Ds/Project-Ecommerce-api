package org.ecommerce.ecommerceapi.client.controller;

import org.ecommerce.ecommerceapi.security.ClientUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.ecommerce.ecommerceapi.client.dto.ClientRequestDTO;
import org.ecommerce.ecommerceapi.client.dto.ClientResponseDTO;
import org.ecommerce.ecommerceapi.client.dto.DeleteClientDTO;
import org.ecommerce.ecommerceapi.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Lista todos os clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> list() {
        List<ClientResponseDTO> clients = clientService.listAllClient();
        return ResponseEntity.ok(clients);
    }

    @Operation(summary = "Busca um cliente pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> searchForIdClient(@PathVariable Long id) {
        ClientResponseDTO client = clientService.searchForIdClient(id);
        return ResponseEntity.ok(client);
    }

    @Operation(summary = "Busca cliente por email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/filtro")
    public ResponseEntity<ClientResponseDTO> searchForEmail(@RequestParam String email) {
        ClientResponseDTO client = clientService.searchForEmail(email);
        return ResponseEntity.ok(client);
    }

    @Operation(summary = "Atualiza os dados do cliente autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping("/me")
    public ResponseEntity<ClientResponseDTO> updateMyData(
            @Valid @RequestBody ClientRequestDTO dto,
            @AuthenticationPrincipal ClientUserDetails userDetails
    ) {
        if (dto.getEmail() != null && !dto.getEmail().equals(userDetails.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        ClientResponseDTO updatedClient = clientService.updateClient(userDetails.getUsername(), dto, true);
        return ResponseEntity.ok(updatedClient);
    }

    @Operation(summary = "Remove o próprio cliente autenticado mediante confirmação da senha")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Senha incorreta"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(
            @Valid @RequestBody DeleteClientDTO dto,
            @AuthenticationPrincipal ClientUserDetails userDetails
    ) {
        if (!passwordEncoder.matches(dto.getSenha(), userDetails.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        clientService.deleteClient(userDetails.getUsername(), true);
        return ResponseEntity.noContent().build();
    }
}
