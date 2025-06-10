package org.ecommerce.ecommerceapi.modules.pedido.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;
    private BigDecimal total;
    private boolean cancelado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;
}
