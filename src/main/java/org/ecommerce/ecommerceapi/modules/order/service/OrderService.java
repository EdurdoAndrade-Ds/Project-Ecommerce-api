package org.ecommerce.ecommerceapi.modules.order.service;

import lombok.*;
import org.ecommerce.ecommerceapi.exceptions.BusinessException;
import org.ecommerce.ecommerceapi.exceptions.ResourceNotFoundException;
import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.ecommerce.ecommerceapi.modules.order.dto.OrderRequestDTO;
import org.ecommerce.ecommerceapi.modules.order.dto.OrderResponseDTO;
import org.ecommerce.ecommerceapi.modules.order.entity.Order;
import org.ecommerce.ecommerceapi.modules.order.entity.OrderItem;
import org.ecommerce.ecommerceapi.modules.order.repository.OrderRepository;
import org.ecommerce.ecommerceapi.modules.order.repository.OrderStatus;
import org.ecommerce.ecommerceapi.modules.product.entity.Product;
import org.ecommerce.ecommerceapi.modules.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final ClientRepository clientRepository;

    @Transactional
    public OrderResponseDTO create(OrderRequestDTO dto, Long clienteId) {
        Order order = new Order();

        ClientEntity cliente = clientRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        order.setCliente(cliente);
        order.setDateCreate(LocalDateTime.now());

        List<OrderItem> itens = dto.getItens().stream().map(itemDTO -> {
            Product product = productService.buscarPorId(itemDTO.getProdutoId());

            if (product.getStock() == null || product.getStock() < itemDTO.getQuantidade()) {
                throw new BusinessException("Estoque insuficiente para o produto: " + product.getName());
            }

            product.setStock(product.getStock() - itemDTO.getQuantidade());

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setNameProduct(product.getName());
            item.setQuantity(itemDTO.getQuantidade());
            item.setUnitPrice(product.getPrice());
            item.setDiscountPrice(product.getDiscountedPrice());

            BigDecimal valorPago = item.getDiscountPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()))
                .setScale(2, RoundingMode.HALF_UP);

            item.setPricePad(valorPago);
            item.setOrder(order);

            return item;
        }).collect(Collectors.toList());

        order.setItens(itens);
        
        // Calcular o total do pedido
        BigDecimal total = itens.stream()
            .map(OrderItem::getPricePad)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .setScale(2, RoundingMode.HALF_UP);

        order.setTotal(total);

        Order salvo = orderRepository.save(order);
        return mapToResponseDTO(salvo);
    }



    public List<OrderResponseDTO> listByClient(Long clienteId) {
        return orderRepository.findByClienteId(clienteId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

        public OrderResponseDTO searchById(Long id, Long clienteId) {
        Order order = orderRepository.findByIdAndClienteId(id, clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado ou acesso negado"));
        return mapToResponseDTO(order);
    }

    @Transactional
    public void cancel(Long id, Long clienteId) {
        Order order = orderRepository.findByIdAndClienteId(id, clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado ou acesso negado"));
        order.setStatus(OrderStatus.CANCELADO);
        orderRepository.save(order);
    }

    public List<OrderResponseDTO> history(Long clienteId) {
        return orderRepository.findByClienteIdAndStatus(clienteId, OrderStatus.CANCELADO).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private OrderResponseDTO mapToResponseDTO(Order order) {
        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(order.getId());
        response.setClienteId(order.getCliente().getId());
        response.setTotal(order.getTotal());
        response.setItens(order.getItens().stream().map(i -> {
            OrderResponseDTO.ItemDTO itemDto = new OrderResponseDTO.ItemDTO();
            itemDto.setProdutoId(i.getProduct().getId());
            itemDto.setNomeProduto(i.getNameProduct());
            itemDto.setQuantidade(i.getQuantity());
            itemDto.setPrecoUnitario(i.getUnitPrice());
            itemDto.setDiscountPrice(i.getDiscountPrice()); // Preço com desconto

            itemDto.setPrecoPago(i.getPricePad());

            return itemDto;
        }).collect(Collectors.toList()));
        return response;
    }
}