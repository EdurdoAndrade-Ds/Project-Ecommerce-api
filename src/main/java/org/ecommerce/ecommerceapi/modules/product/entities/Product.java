package org.ecommerce.ecommerceapi.modules.product.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecommerce.ecommerceapi.modules.pedido.entity.ItemPedido;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "Preço é obrigatório")
    @Min(value = 0, message = "Preço deve ser maior ou igual a zero")
    @Column(name = "preco", nullable = false)
    private BigDecimal preco;

    @NotNull(message = "Estoque é obrigatório")
    @Min(value = 0, message = "Estoque não pode ser negativo")
    @Column(name = "estoque", nullable = false)
    private Integer estoque;

    @OneToMany(mappedBy = "produto")
    private List<ItemPedido> itens;
}
