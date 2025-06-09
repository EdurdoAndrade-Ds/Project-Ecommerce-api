package org.ecommerce.ecommerceapi.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.ecommerce.ecommerceapi.client.model.Client;
import org.ecommerce.ecommerceapi.client.repository.ClientRepository;
import org.ecommerce.ecommerceapi.order.dto.PedidoRequestDTO;
import org.ecommerce.ecommerceapi.order.model.OrderStatus;
import org.ecommerce.ecommerceapi.order.model.Pedido;
import org.ecommerce.ecommerceapi.order.repository.PedidoRepository;
import org.ecommerce.ecommerceapi.product.model.Product;
import org.ecommerce.ecommerceapi.product.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Endpoints de gerenciamento de pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public PedidoController(PedidoRepository pedidoRepository, ProductRepository productRepository, ClientRepository clientRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    @Operation(
            summary = "Lista todos os pedidos",
            description = "Retorna todos os pedidos cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de pedidos retornada com sucesso"
    )
    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Operation(
            summary = "Busca um pedido pelo ID",
            description = "Retorna os dados de um pedido pelo seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedido encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido não encontrado"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Cria um novo pedido",
            description = "Rota responsável por cadastrar um novo pedido no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pedido criado com sucesso",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "Pedido criado",
                                            value = """
                        {
                            "id": 1,
                            "cliente": {
                                "id": 1,
                                "nome": "Maria"
                            },
                            "produtos": [
                                {
                                    "id": 1,
                                    "nome": "Notebook"
                                }
                            ],
                            "status": "NOVO",
                            "createdAt": "2024-06-05T10:00:00"
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
                            "message": "Cliente não encontrado"
                        }
                        """
                                    )
                            }
                    )
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do pedido a ser cadastrado",
            required = true,
            content = @Content(
                    examples = {
                            @ExampleObject(
                                    name = "Cadastro de Pedido",
                                    value = """
                    {
                        "customerId": 1,
                        "produtoIds": [1]
                    }
                    """
                            )
                    }
            )
    )
    @PostMapping
    public ResponseEntity<Pedido> salvar(@Valid @RequestBody PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();

        // Buscar e setar o cliente
        Client cliente = clientRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        pedido.setCliente(cliente);

        // Buscar produtos pelos IDs
        List<Product> produtos = productRepository.findAllById(dto.getProdutoIds());
        pedido.setProdutos(produtos);

        pedido.setStatus(OrderStatus.NOVO);
        pedido.setCreatedAt(LocalDateTime.now());

        Pedido salvo = pedidoRepository.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @Operation(
            summary = "Remove um pedido pelo ID",
            description = "Remove um pedido do sistema pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Pedido removido com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido não encontrado"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        if(!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

