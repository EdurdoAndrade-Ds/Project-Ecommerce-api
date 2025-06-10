package org.ecommerce.ecommerceapi.modules.pagamento.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;

@Data
@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;
    private String status;

    @OneToOne
    private Pedido pedido;

    private LocalDateTime data;
}