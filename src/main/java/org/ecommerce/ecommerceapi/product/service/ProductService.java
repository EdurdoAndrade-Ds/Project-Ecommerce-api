package org.ecommerce.ecommerceapi.product.service;

import org.ecommerce.ecommerceapi.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.product.dto.ProductMapperDTO;
import org.ecommerce.ecommerceapi.product.model.Product;
import org.ecommerce.ecommerceapi.product.repository.ProductRepository;
import org.ecommerce.ecommerceapi.inventory.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Criar produto
    public ProductResponseDTO criar(ProductRequestDTO dto) {
        Product product = ProductMapperDTO.toEntity(dto);

        // Cria inventário vazio ao cadastrar produto
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity(dto.getQuantidadeEstoque() != null ? dto.getQuantidadeEstoque() : 0);

        product.setInventory(inventory);

        Product salvo = productRepository.save(product);
        return ProductMapperDTO.toDTO(salvo);
    }

    // Atualizar produto
    public ProductResponseDTO atualizar(Long id, ProductRequestDTO dto) {
        Product existente = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        existente.setName(dto.getName());
        existente.setDescription(dto.getDescription());
        existente.setPrice(dto.getPrice());

        // Atualiza estoque se vier no DTO
        if (dto.getQuantidadeEstoque() != null) {
            if (existente.getInventory() == null) {
                Inventory inv = new Inventory();
                inv.setProduct(existente);
                existente.setInventory(inv);
            }
            existente.getInventory().setQuantity(dto.getQuantidadeEstoque());
        }

        Product atualizado = productRepository.save(existente);
        return ProductMapperDTO.toDTO(atualizado);
    }

    // Buscar todos os produtos
    public List<ProductResponseDTO> buscarTodos() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapperDTO::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar produto por ID
    public ProductResponseDTO buscarPorId(Long id) {
        Product produto = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return ProductMapperDTO.toDTO(produto);
    }

    // Deletar produto
    public void deletar(Long id) {
        productRepository.deleteById(id);
    }

    // Verificar disponibilidade de estoque
    public boolean verificarEstoque(Long id, int quantidade) {
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.map(p -> {
            Inventory inv = p.getInventory();
            return inv != null && inv.getQuantity() != null && inv.getQuantity() >= quantidade;
        }).orElse(false);
    }

    // Atualizar estoque (reduzir)
    public void atualizarEstoque(Long id, int quantidade) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));

        Inventory inventory = product.getInventory();
        if (inventory == null || inventory.getQuantity() == null) {
            throw new RuntimeException("Estoque não cadastrado para o produto com ID: " + id);
        }

        int estoqueAtual = inventory.getQuantity();
        if (estoqueAtual < quantidade) {
            throw new RuntimeException("Estoque insuficiente para o produto com ID: " + id);
        }

        inventory.setQuantity(estoqueAtual - quantidade);
        productRepository.save(product);
    }
}
