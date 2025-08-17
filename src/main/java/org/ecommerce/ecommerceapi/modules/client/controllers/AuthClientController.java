package org.ecommerce.ecommerceapi.modules.client.controllers;

import org.ecommerce.ecommerceapi.modules.client.dto.AuthClientDTO;
import org.ecommerce.ecommerceapi.modules.client.useCases.AuthClientUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Cliente", description = "Autenticação de cliente")
public class AuthClientController {

    private final AuthClientUseCase authClientUseCase;

    @Autowired
    public AuthClientController(AuthClientUseCase authClientUseCase) {
        this.authClientUseCase = authClientUseCase;
    }

    @PostMapping("/client")
    @Operation(
            summary = "Autenticação de cliente",
            description = "Rota responsável por autenticar um cliente e retornar um token JWT para acesso às rotas protegidas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Autenticação realizada com sucesso",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "Resposta de Sucesso",
                                            value = """
                        {
                            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                            "id": 1,
                            "username": "joaosilva"
                        }
                        """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciais inválidas",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "Erro de Autenticação",
                                            value = """
                        {
                            "message": "Usuário ou senha incorretos"
                        }
                        """
                                    )
                            }
                    )
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Credenciais de autenticação do cliente",
            required = true,
            content = @Content(
                    examples = {
                            @ExampleObject(
                                    name = "Credenciais de Cliente",
                                    value = """
                    {
                        "username": "joaosilva",
                        "senha": "senha123456"
                    }
                    """
                            )
                    }
            )
    )
    public ResponseEntity<Object> auth(@Valid @RequestBody AuthClientDTO authClientDTO) {
        try {
            var result = this.authClientUseCase.execute(authClientDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}

