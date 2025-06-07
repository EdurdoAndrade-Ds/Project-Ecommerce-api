package org.ecommerce.ecommerceapi.pagamento.dto;

import lombok.Data;
import java.time.LocalDateTime;
import org.ecommerce.ecommerceapi.pagamento.model.Pagamento;
import org.ecommerce.ecommerceapi.product.model.Product;

@Data
public class PagamentoResponseDTO {
    private Long id;
    private Long pedidoId;
    private String metodo;
    private String status;
    private LocalDateTime dataPagamento;

    public static PagamentoResponseDTO fromEntity(Pagamento pagamento) {
        PagamentoResponseDTO dto = new PagamentoResponseDTO();
        dto.setId(pagamento.getId());
        dto.setPedidoId(pagamento.getPedido().getId());
        dto.setMetodo(pagamento.getMetodo());
        dto.setStatus(pagamento.getStatus().name());
        dto.setDataPagamento(pagamento.getDataPagamento());
        return dto;
    }
}
