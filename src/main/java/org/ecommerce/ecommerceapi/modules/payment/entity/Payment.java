package org.ecommerce.ecommerceapi.modules.payment.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.ecommerce.ecommerceapi.modules.order.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "data_pagamento", nullable = false)
    private LocalDateTime datePayment;



    
}
