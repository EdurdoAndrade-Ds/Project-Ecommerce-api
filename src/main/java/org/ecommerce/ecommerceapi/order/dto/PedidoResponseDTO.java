package org.ecommerce.ecommerceapi.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {

    @Schema(description = "ID do pedido gerado", example = "1001")
    private Long pedidoId;

    @Schema(description = "ID do cliente", example = "42")
    private Long customerId;

    @Schema(description = "IDs dos produtos que fazem parte do pedido", example = "[101, 102]")
    private List<Long> productIds;

    @Schema(description = "IDs dos produtos que fazem parte do pedido", example = "[101, 102]")
    private String status;

    @Schema(description = "Data e hora em que o pedido foi criado", example = "2025-06-01T10:30:00")
    private LocalDateTime createdAt;

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
