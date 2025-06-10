package org.ecommerce.ecommerceapi.modules.product.service;

import org.ecommerce.ecommerceapi.modules.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.modules.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.modules.product.entities.Product;
import org.ecommerce.ecommerceapi.modules.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
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
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        repository.deleteById(id);
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
        if (dto.getPreco() == null || dto.getPreco().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Preço do produto deve ser maior que zero");
        }
        if (dto.getEstoque() == null || dto.getEstoque() < 0) {
            throw new RuntimeException("Estoque do produto não pode ser negativo");
        }
    }
}
