package org.ecommerce.ecommerceapi.pagamento.model;

import jakarta.persistence.*;
import lombok.*;
import org.ecommerce.ecommerceapi.order.model.Pedido;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private Pedido pedido;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    @Column(nullable = false)
    private String metodo;

    private LocalDateTime dataPagamento;
}
