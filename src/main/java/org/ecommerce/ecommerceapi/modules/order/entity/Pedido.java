package org.ecommerce.ecommerceapi.modules.order.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "tb_pedido")
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
    @JoinColumn(name = "clientes_id", nullable = false)
    private ClientEntity cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> itens;

    // Implementação do equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Verifica se são o mesmo objeto
        if (!(o instanceof Pedido)) return false; // Verifica se o objeto é da mesma classe
        Pedido that = (Pedido) o; // Faz o cast

        // Compara todos os campos relevantes
        return Objects.equals(id, that.id) &&
               cancelado == that.cancelado &&
               Objects.equals(total, that.total) &&
               Objects.equals(dateCreate, that.dateCreate) &&
               Objects.equals(cliente, that.cliente);
    }

    // Implementação do hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, cancelado, total, dateCreate, cliente);
    }

    public ClientEntity getCliente() {
        return cliente;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCliente(ClientEntity cliente) {
        this.cliente = cliente;
    }

    public void setDateCreate(LocalDateTime now) {
        this.dateCreate = now;
    }

    public void setItens(List<OrderItem> itens) {
        this.itens = itens;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setCancelado(boolean b) {
        this.cancelado = b;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
