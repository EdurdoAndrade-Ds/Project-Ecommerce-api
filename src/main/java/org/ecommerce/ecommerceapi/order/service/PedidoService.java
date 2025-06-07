package org.ecommerce.ecommerceapi.order.service;

import org.ecommerce.ecommerceapi.order.model.Pedido;
import org.ecommerce.ecommerceapi.order.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido removerPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o ID: " + id));
        pedidoRepository.delete(pedido);
        return pedido;
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido criaPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido buscaPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o ID: " + id));
    }

    public Pedido atualizaPedido(Long id, Pedido pedidoAtualizado) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o ID: " + id));

        pedidoExistente.setCliente(pedidoAtualizado.getCliente());
        //pedidoExistente.setItens(pedidoAtualizado.getItens());
        //pedidoExistente.setTotal(pedidoAtualizado.getTotal());

        return pedidoRepository.save(pedidoExistente);
    }
}