package org.ecommerce.ecommerceapi.modules.client.controllers;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/client")
@Tag(name = "Cliente", description = "Gestão de clientes")
public class ClientController {

    @Autowired
    private CreateClientUseCase createClientUseCase;
    @Autowired
    private DeleteClientUseCase deleteClientUseCase;
    @Autowired
    private UpdateClientUseCase updateClientUseCase;

    @PostMapping("/")
    @Operation(
            summary = "Cadastro de cliente",
            description = "Rota responsável por cadastrar um novo cliente no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente cadastrado com sucesso",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "Resposta de Sucesso",
                                            value = """
                        {
                            "name": "User Teste",
                            "username": "user",
                            "email": "user@example.com",
                            "password": "12345678",
                            "phone": "11999999999",
                            "address": "Rua Exemplo, 123",
                            "city": "São Paulo",
                            "state": "SP",
                            "cep": "06789-123"
                        }
                        """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "Erro de Validação",
                                            value = """
                        {
                            "message": "Cliente já existe"
                        }
                        """
                                    )
                            }
                    )
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exemplo de requisição para cadastro de cliente",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "ExemploCliente",
                            value = """
            {
              "name": "User Test",
              "username": "user",
              "email": "user@email.com",
              "password": "123456789",
              "telefone": "11999999999",
              "endereco": "Rua Exemplo, 123",
              "cidade": "São Paulo",
              "estado": "SP",
              "cep": "01234-567"
            }
            """
                    )
            )
    )
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

            var result = this.createClientUseCase.execute(clienteEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(
            summary = "Deleção do cliente",
            description = "Rota responsável por deletar/desativar o próprio cliente usando a senha",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Senha incorreta"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Object> delete(
            @Valid @RequestBody DeleteClientDTO deleteClientDTO,
            Authentication authentication
    ) {
        try {
            var clienteId = Long.parseLong(authentication.getName());
            this.deleteClientUseCase.execute(clienteId, deleteClientDTO.getPassword());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(
            summary = "Atualização de dados do cliente",
            description = "Rota responsável por atualizar os dados do próprio cliente",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente atualizado com sucesso",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "Resposta de Sucesso",
                                            value = """
                        {
                            "id": 1,
                            "nome": "João Silva Atualizado",
                            "username": "joaosilva",
                            "email": "novo@email.com",
                            "telefone": "(11) 88888-8888",
                            "endereco": "Rua Nova, 456",
                            "cidade": "São Paulo",
                            "estado": "SP",
                            "cep": "01234-567"
                        }
                        """
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Object> update(
            @Valid @RequestBody UpdateClientDTO updateClientDTO,
            Authentication authentication
    ) {
        Long clienteId = Long.parseLong(authentication.getName());

        var updatedClient = updateClientUseCase.execute(clienteId, updateClientDTO);
        return ResponseEntity.ok(updatedClient);
    }
}

