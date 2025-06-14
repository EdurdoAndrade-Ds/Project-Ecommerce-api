package org.ecommerce.ecommerceapi.modules.pedido.dto;

import java.util.List;
import java.util.Objects;

public class PedidoRequestDTO {
    private List<ItemDTO> itens;

    // Getter e Setter
    public List<ItemDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemDTO> itens) {
        this.itens = itens;
    }

    // equals, hashCode, toString e canEqual para PedidoRequestDTO
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PedidoRequestDTO)) return false;
        PedidoRequestDTO that = (PedidoRequestDTO) o;
        return Objects.equals(itens, that.itens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itens);
    }

    @Override
    public String toString() {
        return "PedidoRequestDTO{" +
                "itens=" + itens +
                '}';
    }

    protected boolean canEqual(Object other) {
        return other instanceof PedidoRequestDTO;
    }

    // Classe interna ItemDTO
    public static class ItemDTO {
        private Long produtoId;
        private Integer quantidade;

        // Getters e Setters
        public Long getProdutoId() {
            return produtoId;
        }

        public void setProdutoId(Long produtoId) {
            this.produtoId = produtoId;
        }

        public Integer getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }

        // equals, hashCode, toString e canEqual para ItemDTO
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ItemDTO)) return false;
            ItemDTO itemDTO = (ItemDTO) o;
            return Objects.equals(produtoId, itemDTO.produtoId) &&
                    Objects.equals(quantidade, itemDTO.quantidade);
        }

        @Override
        public int hashCode() {
            return Objects.hash(produtoId, quantidade);
        }

        @Override
        public String toString() {
            return "ItemDTO{" +
                    "produtoId=" + produtoId +
                    ", quantidade=" + quantidade +
                    '}';
        }

        protected boolean canEqual(Object other) {
            return other instanceof ItemDTO;
        }
    }
}