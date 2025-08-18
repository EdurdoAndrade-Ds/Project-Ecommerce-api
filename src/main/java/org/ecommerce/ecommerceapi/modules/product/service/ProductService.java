package org.ecommerce.ecommerceapi.modules.product.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductStockUpdateRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductUpdateDTO;
import org.ecommerce.ecommerceapi.modules.product.entity.Product;
import org.ecommerce.ecommerceapi.modules.product.repository.ProductRepository;
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
    private static final String MSG_OPERACAO_INVALIDA = "Operação de stock inválida.";
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
        product.setName(dto.getNome());
        product.setDescricao(dto.getDescricao());
        product.setPrice(dto.getPreco());
        product.setStock(dto.getEstoque());

        Product saved = repository.save(product);
        return mapToDTO(saved);
    }

    public List<ProductResponseDTO> listar() {
        return repository.findAll().stream()
                .map(this::mapToDTOWithDiscount) // Certifique-se de usar o método que inclui desconto
                .collect(Collectors.toList());
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

        existente.setName(dto.getNome());
        existente.setDescricao(dto.getDescricao());
        existente.setPrice(dto.getPreco());

        Product atualizado = repository.save(existente);
        return mapToDTO(atualizado);
    }

    private ProductResponseDTO mapToDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setNome(product.getName());
        dto.setDescricao(product.getDescricao());
        dto.setPreco(product.getPrice());
        dto.setEstoque(product.getStock());
        return dto;
    }

    public void validarProduto(ProductRequestDTO dto) {
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
            throw new IllegalArgumentException("Dados de atualização de stock não podem ser nulos.");
        }
        if (dto.getQuantidade() == null || dto.getQuantidade() <= 0) {
            throw new IllegalArgumentException(MSG_QUANTIDADE_MAIOR_ZERO);
        }

        Product produto = buscarPorId(id);

        Integer estoqueAtual = produto.getStock();
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

        produto.setStock(novaQuantidade);
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

    @Transactional
    public ProductResponseDTO aplicarDesconto(Long id, Double pct) {
        if (pct == null) throw new IllegalArgumentException("Porcentagem de desconto é obrigatória");
        // Converte com segurança
        BigDecimal discount = BigDecimal.valueOf(pct);

        // (opcional) normalize para 2 casas
        // discount = discount.setScale(2, RoundingMode.HALF_UP);

        if (discount.compareTo(BigDecimal.ZERO) < 0 ||
                discount.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Porcentagem de desconto inválida");
        }

        Product product = buscarPorId(id);
        product.setDiscountPercentage(discount);   // <<< agora passa BigDecimal
        product = repository.save(product);

        return mapToDTOWithDiscount(product);
    }

    // Método auxiliar que mapeia product para ProductResponseDTO incluindo campos de desconto
    private ProductResponseDTO mapToDTOWithDiscount(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setNome(product.getName());
        dto.setDescricao(product.getDescricao());
        dto.setPreco(product.getPrice());
        dto.setEstoque(product.getStock());

        // Evita BigDecimal vs double no ternário
        dto.setDiscountPercentage(
                product.getDiscountPercentage() == null ? 0.0 : product.getDiscountPercentage().doubleValue()
        );

        // Preço com desconto: deixe a entidade calcular
        dto.setDiscountedPrice(product.getDiscountedPrice());

        return dto;
    }

}