package org.ecommerce.ecommerceapi.order.controller;

import java.util.List;

import org.ecommerce.ecommerceapi.order.model.Order;
import org.ecommerce.ecommerceapi.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> listAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findByIdOrder(Long id) {
        return orderRepository.findById(id);
    }

    public Order save(Order pedido) {
        return orderRepository.save(pedido);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}