package org.ecommerce.ecommerceapi.modules.product.service;

import lombok.Getter;
import lombok.Setter;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductStockUpdateRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductUpdateDTO;
import org.ecommerce.ecommerceapi.modules.product.entities.Product;
import org.ecommerce.ecommerceapi.modules.product.repository.ProductRepository;
import org.ecommerce.ecommerceapi.modules.product.enums.OperacaoEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    private static final String MSG_NOME_OBRIGATORIO = "Nome do produto é obrigatório";
    private static final String MSG_PRECO_INVALIDO = "Preço do produto deve ser maior que zero";
    private static final String MSG_ESTOQUE_NEGATIVO = "Estoque do produto não pode ser negativo";
    private static final String MSG_PRODUTO_NAO_ENCONTRADO = "Produto não encontrado";
    private static final String MSG_QUANTIDADE_MAIOR_ZERO = "A quantidade deve ser maior que zero.";
    private static final String MSG_ESTOQUE_INSUFICIENTE = "Estoque insuficiente para redução.";
    private static final String MSG_OPERACAO_INVALIDA = "Operação de estoque inválida.";
    private static final String MSG_ESTOQUE_NAO_DEFINIDO = "Estoque do produto não está definido.";

    public Product buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException(MSG_PRODUTO_NAO_ENCONTRADO));
    }

    public ProductResponseDTO buscarPorIdDTO(Long id) {
        Product product = buscarPorId(id);
        return mapToDTO(product);
    }

    public ProductResponseDTO criar(ProductRequestDTO dto) {
        validarProduto(dto);

        Product product = new Product();
        product.setNome(dto.getNome());
        product.setDescricao(dto.getDescricao());
        product.setPreco(dto.getPreco());
        product.setEstoque(dto.getEstoque());

        Product saved = repository.save(product);
        return mapToDTO(saved);
    }

    public List<ProductResponseDTO> listar() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public void excluir(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do produto não pode ser nulo");
        }
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException(MSG_PRODUTO_NAO_ENCONTRADO);
        }
        repository.deleteById(id);
    }

    public ProductResponseDTO atualizar(Long id, ProductUpdateDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException(MSG_NOME_OBRIGATORIO);
        }
        if (dto.getPreco() == null || dto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(MSG_PRECO_INVALIDO);
        }

        Product existente = buscarPorId(id);

        existente.setNome(dto.getNome());
        existente.setDescricao(dto.getDescricao());
        existente.setPreco(dto.getPreco());

        Product atualizado = repository.save(existente);
        return mapToDTO(atualizado);
    }

    private ProductResponseDTO mapToDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setNome(product.getNome());
        dto.setDescricao(product.getDescricao());
        dto.setPreco(product.getPreco());
        dto.setEstoque(product.getEstoque());
        return dto;
    }

    private void validarProduto(ProductRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException(MSG_NOME_OBRIGATORIO);
        }
        if (dto.getPreco() == null || dto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(MSG_PRECO_INVALIDO);
        }
        if (dto.getEstoque() == null || dto.getEstoque() < 0) {
            throw new IllegalArgumentException(MSG_ESTOQUE_NEGATIVO);
        }
    }

    public void atualizarEstoque(Long id, ProductStockUpdateRequestDTO dto) {
        if(dto == null) {
            throw new IllegalArgumentException("Dados de atualização de estoque não podem ser nulos.");
        }
        if (dto.getQuantidade() == null || dto.getQuantidade() <= 0) {
            throw new IllegalArgumentException(MSG_QUANTIDADE_MAIOR_ZERO);
        }

        Product produto = buscarPorId(id);

        Integer estoqueAtual = produto.getEstoque();
        if (estoqueAtual == null) {
            throw new IllegalStateException(MSG_ESTOQUE_NAO_DEFINIDO);
        }

        int novaQuantidade;
        switch (dto.getOperacao()) {
            case AUMENTAR:
                novaQuantidade = estoqueAtual + dto.getQuantidade();
                break;
            case REDUZIR:
                if (dto.getQuantidade() > estoqueAtual) {
                    throw new IllegalArgumentException(MSG_ESTOQUE_INSUFICIENTE);
                }
                novaQuantidade = estoqueAtual - dto.getQuantidade();
                break;
            default:
                throw new IllegalArgumentException(MSG_OPERACAO_INVALIDA);
        }

        produto.setEstoque(novaQuantidade);
        repository.save(produto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductService)) return false;
        ProductService that = (ProductService) o;
        return Objects.equals(repository, that.repository);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repository);
    }

    protected boolean canEqual(Object other) {
        return other instanceof ProductService;
    }
}