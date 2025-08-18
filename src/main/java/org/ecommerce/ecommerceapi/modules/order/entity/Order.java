package org.ecommerce.ecommerceapi.modules.order.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Getter @Setter
@Table(name = "tb_pedido")
public class Order {

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> itens = new ArrayList<>();

    // Implementação do equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Verifica se são o mesmo objeto
        if (!(o instanceof Order)) return false; // Verifica se o objeto é da mesma classe
        Order that = (Order) o; // Faz o cast

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
}
