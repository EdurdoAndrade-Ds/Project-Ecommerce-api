package org.ecommerce.ecommerceapi.product.controller;

import org.ecommerce.ecommerceapi.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.product.service.ProductService;
import org.ecommerce.ecommerceapi.pagamento.dto.PagamentoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Tag(name = "Produto", description = "Endpoints de gerenciamento de produtos")
@RestController
@RequestMapping("/api/produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(
        summary = "Cria um novo produto",
        description = "Rota responsável por cadastrar um novo produto no sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Produto cadastrado com sucesso",
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "Produto criado",
                        value = """
                        {
                            "id": 1,
                            "nome": "Notebook",
                            "descricao": "Notebook Dell Inspiron",
                            "preco": 3500.00,
                            "estoque": 10
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
                            "message": "Nome do produto já cadastrado"
                        }
                        """
                    )
                }
            )
        )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dados do produto a ser cadastrado",
        required = true,
        content = @Content(
            examples = {
                @ExampleObject(
                    name = "Cadastro de Produto",
                    value = """
                    {
                        "nome": "Notebook",
                        "descricao": "Notebook Dell Inspiron",
                        "preco": 3500.00,
                        "estoque": 10
                    }
                    """
                )
            }
        )
    )
    @PostMapping
    public ProductResponseDTO criar(@RequestBody ProductRequestDTO dto) {
        return productService.criar(dto);
    }

    @Operation(
        summary = "Atualizar produto",
        description = "Atualiza os dados de um produto existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produto atualizado com sucesso",
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "Produto atualizado",
                        value = """
                        {
                            "id": 1,
                            "nome": "Notebook Atualizado",
                            "descricao": "Notebook Dell Inspiron 2024",
                            "preco": 3700.00,
                            "estoque": 15
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        )
    })
    @PutMapping("/{id}")
    public ProductResponseDTO atualizar(@PathVariable Long id, @RequestBody ProductRequestDTO dto) {
        return productService.atualizar(id, dto);
    }

    @Operation(
        summary = "Buscar todos os produtos",
        description = "Retorna a lista de todos os produtos cadastrados"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de produtos retornada com sucesso"
    )
    @GetMapping
    public List<ProductResponseDTO> buscarTodos() {
        return productService.buscarTodos();
    }

    @Operation(
        summary = "Buscar produto por ID",
        description = "Retorna os dados de um produto pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produto encontrado"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        )
    })
    @GetMapping("/{id}")
    public ProductResponseDTO buscarPorId(@PathVariable Long id) {
        return productService.buscarPorId(id);
    }

    @Operation(
        summary = "Deletar produto",
        description = "Remove um produto do sistema pelo ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Produto deletado com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        )
    })
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        productService.deletar(id);
    }

    @Operation(
        summary = "Verificar disponibilidade de estoque",
        description = "Verifica se há quantidade suficiente de um produto em estoque"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Disponibilidade retornada"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        )
    })
    @GetMapping("/{id}/disponivel")
    public boolean verificarEstoque(@PathVariable Long id, @RequestParam int quantidade) {
        return productService.verificarEstoque(id, quantidade);
    }

    @Operation(
        summary = "Adicionar ao estoque",
        description = "Adiciona uma quantidade ao estoque do produto"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Estoque atualizado com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        )
    })
    @PutMapping("/{id}/estoque/adicionar")
    public ResponseEntity<Void> adicionarEstoque(@PathVariable Long id, @RequestParam int quantidade) {
        productService.adicionarAoEstoque(id, quantidade);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Reduzir estoque",
        description = "Reduz a quantidade de um produto em estoque"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Estoque reduzido com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        )
    })
    @PutMapping("/{id}/reduzir-estoque")
    public void atualizarEstoque(@PathVariable Long id, @RequestParam int quantidade) {
        productService.atualizarEstoque(id, quantidade);
    }
}