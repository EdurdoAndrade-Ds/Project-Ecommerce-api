package org.ecommerce.ecommerceapi.modules.pedido.entity;

import jakarta.persistence.*;
import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cancelado", nullable = false)
    private boolean cancelado = false;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "date_create", nullable = false)
    private LocalDateTime dateCreate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> items;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItens() {
        return items;
    }

    public void setItens(List<ItemPedido> items) {
        this.items = items;
    }

    // equals e hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        Pedido pedido = (Pedido) o;
        return cancelado == pedido.cancelado &&
                Objects.equals(id, pedido.id) &&
                Objects.equals(total, pedido.total) &&
                Objects.equals(dateCreate, pedido.dateCreate) &&
                Objects.equals(cliente, pedido.cliente) &&
                Objects.equals(items, pedido.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cancelado, total, dateCreate, cliente, items);
    }

    // toString

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cancelado=" + cancelado +
                ", total=" + total +
                ", dateCreate=" + dateCreate +
                ", cliente=" + cliente +
                ", items=" + items +
                '}';
    }
}
