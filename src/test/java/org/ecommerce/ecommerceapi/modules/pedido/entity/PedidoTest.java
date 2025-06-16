package org.ecommerce.ecommerceapi.modules.pedido.entity;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Pedido pedido;
    private ClienteEntity cliente;
    private ItemPedido itemPedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        cliente = new ClienteEntity(); // Supondo que você tenha um construtor padrão em ClienteEntity
        cliente.setId(1L);
        cliente.setNome("Cliente A"); // Supondo que você tenha um método setNome

        itemPedido = new ItemPedido();
        itemPedido.setId(1L);
        itemPedido.setNomeProduto("Produto A");
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoUnitario(BigDecimal.valueOf(50.00));
        itemPedido.setDescountPrice(BigDecimal.valueOf(45.00));
        itemPedido.setPrecoPago(BigDecimal.valueOf(90.00));
        itemPedido.setPedido(pedido); // Associando o item ao pedido

        List<ItemPedido> itens = new ArrayList<>();
        itens.add(itemPedido);

        pedido.setId(1L);
        pedido.setCancelado(false);
        pedido.setTotal(BigDecimal.valueOf(90.00));
        pedido.setDateCreate(LocalDateTime.now());
        pedido.setCliente(cliente);
        pedido.setItens(itens);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, pedido.getId());
        assertFalse(pedido.isCancelado());
        assertTrue(pedido.getTotal().compareTo(BigDecimal.valueOf(90.00)) == 0);
        assertNotNull(pedido.getDateCreate());
        assertEquals(cliente, pedido.getCliente());
        assertNotNull(pedido.getItens());
        assertEquals(1, pedido.getItens().size());
        assertEquals(itemPedido, pedido.getItens().get(0));
    }

    
}
