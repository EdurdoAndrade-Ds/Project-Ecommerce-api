package org.ecommerce.ecommerceapi.pedido.service;

import java.util.List;

import org.ecommerce.ecommerceapi.exceptions.ResourceNotFoundException;
import org.ecommerce.ecommerceapi.Pedido.model.Pedido;
import org.ecommerce.ecommerceapi.Pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository PedidoRepository;

    public Pedido savePedido(Pedido pedido) {
        return PedidoRepository.save(pedido);
    }

    public List<Pedido> listAllPedidos() {
        return PedidoRepository.findAll();
    }

    public Pedido searchForIdPedido(Long id) {
        return PedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id " + id));
    }

    public Pedido updatePedido(Long id, Pedido pedidoUpdated) {
        Pedido pedido = searchForIdPedido(id);
        pedido.setDescricao(pedidoUpdated.getDescricao());
        pedido.setValorTotal(pedidoUpdated.getValorTotal());
        pedido.setStatus(pedidoUpdated.getStatus());
        return PedidoRepository.save(pedido);
    }

    public void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
