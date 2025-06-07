package org.ecommerce.ecommerceapi.pagamento.controller;

import org.ecommerce.ecommerceapi.pagamento.dto.PagamentoRequestDTO;
import org.ecommerce.ecommerceapi.pagamento.dto.PagamentoResponseDTO;
import org.ecommerce.ecommerceapi.pagamento.service.PagamentoService;
import org.ecommerce.ecommerceapi.security.ClientUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "Pagamento", description = "Endpoints para processamento de pagamentos")
@RestController
@RequestMapping("/api/pagamentos")
public class PagamentController {

    @Autowired
    private PagamentoService pagamentoService;

    @Operation(
        summary = "Realiza um pagamento",
        description = "Rota responsável por processar um pagamento no sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Pagamento realizado com sucesso",
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "Pagamento realizado",
                        value = """
                        {
                            "id": 1,
                            "valor": 3500.00,
                            "status": "APROVADO",
                            "metodo": "CARTAO_CREDITO",
                            "pedidoId": 10
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
                            "message": "Cartão inválido ou saldo insuficiente"
                        }
                        """
                    )
                }
            )
        )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dados do pagamento a ser realizado",
        required = true,
        content = @Content(
            examples = {
                @ExampleObject(
                    name = "Pagamento",
                    value = """
                    {
                        "valor": 3500.00,
                        "metodo": "CARTAO_CREDITO",
                        "pedidoId": 10,
                        "dadosCartao": {
                            "numero": "1234 5678 9012 3456",
                            "nomeTitular": "Maria Silva",
                            "validade": "12/28",
                            "cvv": "123"
                        }
                    }
                    """
                )
            }
        )
    )
    @PostMapping
    public ResponseEntity<?> realizarPagamento(
        @RequestBody PagamentoRequestDTO dto,
        @AuthenticationPrincipal ClientUserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        // Exemplo de chamada ao serviço:
        PagamentoResponseDTO response = pagamentoService.processarPagamento(dto);
        return ResponseEntity.ok(response);
    }
}
