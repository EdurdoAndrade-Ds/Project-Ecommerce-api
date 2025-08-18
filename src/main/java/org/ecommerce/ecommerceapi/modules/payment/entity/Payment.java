package org.ecommerce.ecommerceapi.modules.payment.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.ecommerce.ecommerceapi.modules.order.entity.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "pagamento")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "data_pagamento", nullable = false)
    private LocalDateTime dataPagamento;

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setDataPagamento(LocalDateTime now) {
        this.dataPagamento = now;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Long getId() {
        return id;
    }

    
}
