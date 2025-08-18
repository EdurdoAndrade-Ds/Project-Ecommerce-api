package org.ecommerce.ecommerceapi.modules.payment.repository;

import org.ecommerce.ecommerceapi.modules.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByOrderId(Long pedidoId);
}
