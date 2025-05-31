package org.ecommerce.ecommerceapi.product.service;

import org.ecommerce.ecommerceapi.product.model.Product;
import org.ecommerce.ecommerceapi.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Criar ou atualizar produto
    public Product salvar(Product product) {
        return productRepository.save(product);
    }

    // Buscar todos os produtos
    public List<Product> buscarTodos() {
        return productRepository.findAll();
    }

    // Buscar produto por ID
    public Optional<Product> buscarPorId(Long id) {
        return productRepository.findById(id);
    }

    // Atualizar produto
    public Product atualizar(Product product) {
        if (product.getId() == null) {
            throw new IllegalArgumentException("Não é possível atualizar um produto sem ID");
        }
        return productRepository.save(product);
    }

    // Deletar produto
    public void deletar(Long id) {
        productRepository.deleteById(id);
    }

    // Verificar disponibilidade de estoque
    public boolean verificarEstoque(Long id, int quantidade) {
        Optional<Product> product = buscarPorId(id);
        return product.map(p -> {
            Integer estoque = p.getQuantidadeEstoque();
            return estoque != null && estoque >= quantidade;
        }).orElse(false);
    }

    // Atualizar estoque
    public void atualizarEstoque(Long id, int quantidade) {
        Product product = buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));

        Integer estoqueAtual = product.getQuantidadeEstoque();
        if (estoqueAtual == null) {
            throw new RuntimeException("Estoque do produto não está definido para o produto com ID: " + id);
        }

        int novoEstoque = estoqueAtual - quantidade;
        if (novoEstoque < 0) {
            throw new RuntimeException("Estoque insuficiente para o produto com ID: " + id + ". Estoque atual: " + estoqueAtual + ", solicitado: " + quantidade);
        }

        product.setQuantidadeEstoque(novoEstoque);
        productRepository.save(product);
    }
}