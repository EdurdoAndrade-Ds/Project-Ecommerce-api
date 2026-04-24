package org.ecommerce.ecommerceapi.modules.payment.controller;

import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentRequestDTO;
import org.ecommerce.ecommerceapi.modules.payment.dto.PaymentResponseDTO;
import org.ecommerce.ecommerceapi.modules.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamentos")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PaymentResponseDTO> pay(@RequestBody PaymentRequestDTO dto, Authentication authentication) {
        Long clienteId = Long.parseLong(authentication.getName());
        return new ResponseEntity<>(paymentService.pay(dto, clienteId), HttpStatus.CREATED);
    }
}
