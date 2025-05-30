package org.ecommerce.ecommerceapi.product.service;

import com.ecommerce.ecommerce.modules.Produto;
import com.ecommerce.ecommerce.repository.ProdutoRepository;
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
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    // Buscar todos os produtos
    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }

    // Buscar produto por ID
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    // Atualizar produto
    public Produto atualizar(Produto produto) {
        if (produto.getId() == null) {
            throw new IllegalArgumentException("Não é possível atualizar um produto sem ID");
        }
        return produtoRepository.save(produto);
    }

    // Deletar produto
    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    // Verificar disponibilidade de estoque
    public boolean verificarEstoque(Long id, int quantidade) {
        Optional<Produto> produto = buscarPorId(id);
        return produto.map(p -> p.getQuantidadeEstoque() >= quantidade).orElse(false);
    }

    // Atualizar estoque
    public void atualizarEstoque(Long id, int quantidade) {
        Produto produto = buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        int novoEstoque = produto.getQuantidadeEstoque() - quantidade;
        if (novoEstoque < 0) {
            throw new RuntimeException("Estoque insuficiente");
        }

        produto.setQuantidadeEstoque(novoEstoque);
        produtoRepository.save(produto);
    }
}