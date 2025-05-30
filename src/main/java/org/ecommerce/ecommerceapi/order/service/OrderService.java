package org.ecommerce.ecommerceapi.order.service;

import org.ecommerce.ecommerceapi.order.model.Order;
import org.ecommerce.ecommerceapi.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> listarTodos() {
        return orderRepository.findAll();
    }

    public Optional<Order> buscarPorId(Long id) {
        return orderRepository.findById(id);
    }

    public Order salvar(Order pedido) {
        return orderRepository.save(pedido);
    }

    public void deletar(Long id) {
        orderRepository.deleteById(id);
    }
}}