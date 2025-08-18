package org.ecommerce.ecommerceapi.modules.client.controllers;

import org.ecommerce.ecommerceapi.exceptions.ClientUnauthorizedException;
import org.ecommerce.ecommerceapi.modules.client.useCases.ProfileClientUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import java.util.function.LongPredicate;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Cliente", description = "Perfil do cliente")
public class ProfileClientController {

    private final ProfileClientUseCase profileClientUseCase;

    public ProfileClientController(ProfileClientUseCase profileClientUseCase) {
        this.profileClientUseCase = profileClientUseCase;
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(
            summary = "Perfil do cliente",
            description = "Rota responsável por retornar os dados do perfil do cliente autenticado",
            security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil retornado com sucesso",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "Resposta de Sucesso",
                                            value = """
                        {
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
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Object> profile(HttpServletRequest request) {
            Object idAttr = request.getAttribute("cliente_id");
            if(idAttr == null) {
                throw new ClientUnauthorizedException("Token invalido ou ausente");
            }

            long clienteId = Long.parseLong(idAttr.toString());
            return ResponseEntity.ok(profileClientUseCase.execute(clienteId));
    }
}
