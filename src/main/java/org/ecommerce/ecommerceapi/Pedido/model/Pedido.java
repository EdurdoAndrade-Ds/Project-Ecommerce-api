package org.ecommerce.ecommerceapi.Pedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.ClientEndpoint;
import org.ecommerce.ecommerceapi.client.model.Client;
import org.ecommerce.ecommerceapi.product.model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {
    private Long id;

    private String cliente;
    private String produto;
    private int quantidade;
    private double preco;
    private int dataPedido;
    private int estoque;

     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(int dataPedido) {
        this.dataPedido = dataPedido;
    }

   public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    } 
}

// cliente, numero, quantidade, estoque, preço 