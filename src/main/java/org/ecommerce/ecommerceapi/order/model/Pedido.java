package org.ecommerce.ecommerceapi.order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.ClientEndpoint;
import org.ecommerce.ecommerceapi.client.model.Client;
import org.ecommerce.ecommerceapi.product.model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime dateCreate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Client client;

    @ManyToMany
    @JoinTable(
            name = "pedido_produto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Product> products = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(LocalDateTime dateCreate, OrderStatus status, Client client, List<Product> products) {
        this.dateCreate = dateCreate;
        this.status = status;
        this.client = client;
        this.products = products;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Client getCliente() {
        return client;
    }

    public void setCliente(Client client) {
        this.client = client;
    }

    public List<Product> getProdutos() {
        return products;
    }

    public void setProdutos(List<Product> products) {
        this.products = products;
    }
}