package org.ecommerce.ecommerceapi.modules.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.ecommerce.ecommerceapi.modules.product.enums.OperacaoEstoque;

import java.util.Objects;

public class ProductStockUpdateRequestDTO {

    @Schema(
        description = "Operação de estoque: AUMENTAR ou REDUZIR",
        example = "AUMENTAR"
    )
    private OperacaoEstoque operacao;

    @Schema(
        description = "Quantidade a ser aplicada na operação de estoque",
        example = "10"
    )
    private Integer quantidade;

    // Getters e Setters
    public OperacaoEstoque getOperacao() {
        return operacao;
    }

    public void setOperacao(OperacaoEstoque operacao) {
        this.operacao = operacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductStockUpdateRequestDTO)) return false;
        ProductStockUpdateRequestDTO that = (ProductStockUpdateRequestDTO) o;
        return operacao == that.operacao &&
               Objects.equals(quantidade, that.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operacao, quantidade);
    }

    @Override
    public String toString() {
        return "ProductStockUpdateRequestDTO{" +
               "operacao=" + operacao +
               ", quantidade=" + quantidade +
               '}';
    }

    // Método canEqual para verificar igualdade
    protected boolean canEqual(Object other) {
        return other instanceof ProductStockUpdateRequestDTO;
    }
}
