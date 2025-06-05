package org.ecommerce.ecommerceapi.pagamento.controller;

import org.ecommerce.ecommerceapi.pagamento.dto.PagamentoRequestDTO;
import org.ecommerce.ecommerceapi.pagamento.dto.PagamentoResponseDTO;
import org.ecommerce.ecommerceapi.pagamento.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public PagamentoResponseDTO realizarPagamento(@RequestBody PagamentoRequestDTO dto) {
        return pagamentoService.processarPagamento(dto);
    }
}
