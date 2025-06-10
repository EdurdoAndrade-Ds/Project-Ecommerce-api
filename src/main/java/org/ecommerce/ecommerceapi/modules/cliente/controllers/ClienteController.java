package org.ecommerce.ecommerceapi.modules.cliente.controllers;

import org.ecommerce.ecommerceapi.modules.cliente.dto.CreateClienteDTO;
import org.ecommerce.ecommerceapi.modules.cliente.dto.DeleteClienteDTO;
import org.ecommerce.ecommerceapi.modules.cliente.dto.UpdateClienteDTO;
import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.useCases.CreateClienteUseCase;
import org.ecommerce.ecommerceapi.modules.cliente.useCases.DeleteClienteUseCase;
import org.ecommerce.ecommerceapi.modules.cliente.useCases.UpdateClienteUseCase;
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
@RequestMapping("/cliente")
@Tag(name = "Cliente", description = "Gestão de clientes")
public class ClienteController {

    @Autowired
    private CreateClienteUseCase createClienteUseCase;
    @Autowired
    private DeleteClienteUseCase deleteClienteUseCase;
    @Autowired
    private UpdateClienteUseCase updateClienteUseCase;

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
                            "id": 1,
                            "nome": "João Silva",
                            "username": "joaosilva",
                            "email": "joao@email.com",
                            "telefone": "(11) 99999-9999",
                            "endereco": "Rua das Flores, 123",
                            "cidade": "São Paulo",
                            "estado": "SP",
                            "cep": "01234-567"
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
    public ResponseEntity<Object> create(@Valid @RequestBody CreateClienteDTO createClienteDTO) {
        try {
            var clienteEntity = ClienteEntity.builder()
                    .nome(createClienteDTO.getNome())
                    .username(createClienteDTO.getUsername())
                    .email(createClienteDTO.getEmail())
                    .senha(createClienteDTO.getSenha())
                    .telefone(createClienteDTO.getTelefone())
                    .endereco(createClienteDTO.getEndereco())
                    .cidade(createClienteDTO.getCidade())
                    .estado(createClienteDTO.getEstado())
                    .cep(createClienteDTO.getCep())
                    .build();

            var result = this.createClienteUseCase.execute(clienteEntity);
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
            @Valid @RequestBody DeleteClienteDTO deleteClienteDTO,
            Authentication authentication
    ) {
        try {
            var clienteId = Long.parseLong(authentication.getName());
            this.deleteClienteUseCase.execute(clienteId, deleteClienteDTO.getSenha());
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
            @Valid @RequestBody UpdateClienteDTO updateClienteDTO,
            Authentication authentication
    ) {
        try {
            var clienteId = Long.parseLong(authentication.getName());
            var updatedCliente = this.updateClienteUseCase.execute(clienteId, updateClienteDTO);
            return ResponseEntity.ok().body(updatedCliente);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

