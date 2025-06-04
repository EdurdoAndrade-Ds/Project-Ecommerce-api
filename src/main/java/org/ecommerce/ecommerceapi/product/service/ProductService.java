package org.ecommerce.ecommerceapi.product.service;

import org.ecommerce.ecommerceapi.product.dto.ProductRequestDTO;
import org.ecommerce.ecommerceapi.product.dto.ProductResponseDTO;
import org.ecommerce.ecommerceapi.product.dto.ProductMapperDTO;
import org.ecommerce.ecommerceapi.product.model.Product;
import org.ecommerce.ecommerceapi.inventory.model.Inventory;
import org.ecommerce.ecommerceapi.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Criar produto com estoque
    public ProductResponseDTO criar(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        Inventory inventory = new Inventory();
        inventory.setQuantity(dto.getStockQuantity());
        inventory.setProduct(product);

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

        if (existente.getInventory() != null) {
            existente.getInventory().setQuantity(dto.getStockQuantity());
        } else {
            Inventory inventory = new Inventory();
            inventory.setQuantity(dto.getStockQuantity());
            inventory.setProduct(existente);
            existente.setInventory(inventory);
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

    // Buscar por ID
    public ProductResponseDTO buscarPorId(Long id) {
        Product produto = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return ProductMapperDTO.toDTO(produto);
    }

    // Deletar
    public void deletar(Long id) {
        productRepository.deleteById(id);
    }

    // Adicionar ao estoque
    public void adicionarAoEstoque(Long id, int quantidade) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Inventory inventory = product.getInventory();
        if (inventory == null) {
            inventory = new Inventory();
            inventory.setQuantity(quantidade);
            inventory.setProduct(product);
            product.setInventory(inventory);
        } else {
            inventory.setQuantity(inventory.getQuantity() + quantidade);
        }

        productRepository.save(product);
    }

    // Reduzir estoque
    public void atualizarEstoque(Long id, int quantidade) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Inventory inventory = product.getInventory();
        if (inventory == null || inventory.getQuantity() < quantidade) {
            throw new RuntimeException("!!ERRO: Estoque insuficiente!!");
        }

        inventory.setQuantity(inventory.getQuantity() - quantidade);
        productRepository.save(product);
    }

    // Verificar disponibilidade de estoque
    public boolean verificarEstoque(Long id, int quantidade) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Inventory inventory = product.getInventory();
        return inventory != null && inventory.getQuantity() >= quantidade;
    }
}
