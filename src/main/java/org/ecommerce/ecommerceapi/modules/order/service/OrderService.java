package org.ecommerce.ecommerceapi.modules.order.service;

import lombok.Data;
import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.ecommerce.ecommerceapi.modules.order.dto.OrderRequestDTO;
import org.ecommerce.ecommerceapi.modules.order.dto.OrderResponseDTO;
import org.ecommerce.ecommerceapi.modules.order.entity.OrderItem;
import org.ecommerce.ecommerceapi.modules.order.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.order.repository.OrderRepository;
import org.ecommerce.ecommerceapi.modules.product.entity.Product;
import org.ecommerce.ecommerceapi.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientRepository clientRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductService productService,
                        ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.clientRepository = clientRepository;
    }

    public OrderResponseDTO create(OrderRequestDTO dto, Long clienteId) {
        Pedido pedido = new Pedido();

        ClientEntity cliente = clientRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        pedido.setCliente(cliente);
        pedido.setDateCreate(LocalDateTime.now());

        List<OrderItem> itens = dto.getItens().stream().map(itemDTO -> {
            Product product = productService.buscarPorId(itemDTO.getProdutoId());
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setNameProduct(product.getNome());
            item.setQuantity(itemDTO.getQuantidade());


            item.setUnitPrice(product.getPreco());
            item.setDiscountPrice(product.getDiscountedPrice());

            BigDecimal valorPago = item.getDiscountPrice()
    .multiply(BigDecimal.valueOf(item.getQuantity()))
    .setScale(2, RoundingMode.HALF_UP);

item.setPricePad(valorPago);


            BigDecimal precoPago = item.getDiscountPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            item.setPricePad(precoPago);



            item.setPedido(pedido);
            return item;
        }).collect(Collectors.toList());

        pedido.setItens(itens);
        
        // Calcular o total do pedido
        BigDecimal total = itens.stream()
    .map(OrderItem::getPricePad)
    .reduce(BigDecimal.ZERO, BigDecimal::add)
    .setScale(2, RoundingMode.HALF_UP);

pedido.setTotal(total);

        Pedido salvo = orderRepository.save(pedido);
        return mapToResponseDTO(salvo);
    }



    public List<OrderResponseDTO> listByClient(Long clienteId) {
        return orderRepository.findByClienteId(clienteId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

        public OrderResponseDTO searchById(Long id, Long clienteId) {
        Pedido pedido = orderRepository.findByIdAndClienteId(id, clienteId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado ou acesso negado"));
        return mapToResponseDTO(pedido);
    }

    public void cancel(Long id, Long clienteId) {
        Pedido pedido = orderRepository.findByIdAndClienteId(id, clienteId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado ou acesso negado"));
        pedido.setCancelado(true);
        orderRepository.save(pedido);
    }

    public List<OrderResponseDTO> history(Long clienteId) {
        return orderRepository.findByClienteIdAndCanceladoTrue(clienteId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private OrderResponseDTO mapToResponseDTO(Pedido pedido) {
        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(pedido.getId());
        response.setClienteId(pedido.getCliente().getId());
        response.setTotal(pedido.getTotal());
        response.setItens(pedido.getItens().stream().map(i -> {
            OrderResponseDTO.ItemDTO itemDto = new OrderResponseDTO.ItemDTO();
            itemDto.setProdutoId(i.getProduct().getId());
            itemDto.setNomeProduto(i.getNameProduct());
            itemDto.setQuantidade(i.getQuantity());
            itemDto.setPrecoUnitario(i.getUnitPrice());
            itemDto.setDiscountPrice(i.getDiscountPrice()); // Preço com desconto

            itemDto.setPrecoPago(i.getPricePad()); // Total pago por este item
            itemDto.setPrecoPago(i.getPricePad()); // Preço total pago pelo item

            return itemDto;
        }).collect(Collectors.toList()));
        return response;
    }
}