package org.ecommerce.ecommerceapi.modules.product.service;

import lombok.Data;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductStockUpdateRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductUpdateDTO;
import org.ecommerce.ecommerceapi.modules.product.entities.Product;
import org.ecommerce.ecommerceapi.modules.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
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
        return repository.findAll().stream().map(this::mapToDTO)
.collect(Collectors.toList());
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        repository.deleteById(id);
    }

    // 🔁 NOVO MÉTODO DE ATUALIZAÇÃO SEM ESTOQUE
    public ProductResponseDTO atualizar(Long id, ProductUpdateDTO dto) {
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome do produto é obrigatório");
        }
        if (dto.getPreco() == null || dto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Preço do produto deve ser maior que zero");
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
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome do produto é obrigatório");
        }
        if (dto.getPreco() == null || dto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Preço do produto deve ser maior que zero");
        }
        if (dto.getEstoque() == null || dto.getEstoque() < 0) {
            throw new RuntimeException("Estoque do produto não pode ser negativo");
        }
    }
    public void atualizarEstoque(Long id, ProductStockUpdateRequestDTO dto) {
    Product produto = buscarPorId(id);

    if (dto.getQuantidade() == null || dto.getQuantidade() <= 0) {
        throw new RuntimeException("A quantidade deve ser maior que zero.");
    }

    Integer estoqueAtual = produto.getEstoque();
    Integer novaQuantidade;

    switch (dto.getOperacao()) {
        case AUMENTAR:
            novaQuantidade = estoqueAtual + dto.getQuantidade();
            break;
        case REDUZIR:
            if (dto.getQuantidade() > estoqueAtual) {
                throw new RuntimeException("Estoque insuficiente para redução.");
            }
            novaQuantidade = estoqueAtual - dto.getQuantidade();
            break;
        default:
            throw new RuntimeException("Operação de estoque inválida.");
    }

    produto.setEstoque(novaQuantidade);
    repository.save(produto);
    
    }
    
}
