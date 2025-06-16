package org.ecommerce.ecommerceapi.modules.pedido.service;

import lombok.Data;
import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoRequestDTO;
import org.ecommerce.ecommerceapi.modules.pedido.dto.PedidoResponseDTO;
import org.ecommerce.ecommerceapi.modules.pedido.entity.ItemPedido;
import org.ecommerce.ecommerceapi.modules.pedido.entity.Pedido;
import org.ecommerce.ecommerceapi.modules.pedido.repository.PedidoRepository;
import org.ecommerce.ecommerceapi.modules.product.entity.Product;
import org.ecommerce.ecommerceapi.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClienteRepository clienteRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ProductService productService,
                         ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productService = productService;
        this.clienteRepository = clienteRepository;
    }

    public PedidoResponseDTO criar(PedidoRequestDTO dto, Long clienteId) {
        Pedido pedido = new Pedido();

        ClienteEntity cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        pedido.setCliente(cliente);
        pedido.setDateCreate(LocalDateTime.now());

        List<ItemPedido> itens = dto.getItens().stream().map(itemDTO -> {
            Product product = productService.buscarPorId(itemDTO.getProdutoId());
            ItemPedido item = new ItemPedido();
            item.setProduto(product);
            item.setNomeProduto(product.getNome());
            item.setQuantidade(itemDTO.getQuantidade());

            // Definir o preço unitário como o preço original do produto
            item.setPrecoUnitario(product.getPreco()); // Preço original
            item.setDescountPrice(product.getDescountedPrice()); // Preço com desconto

            item.setPedido(pedido);
            return item;
        }).collect(Collectors.toList());

        pedido.setItens(itens);
        
        // Calcular o total do pedido
        BigDecimal total = itens.stream()
                .map(item -> item.getDescountPrice().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        pedido.setTotal(total);

        // Definir precoPago como o total do pedido
        for (ItemPedido item : itens) {
            item.setPrecoPago(total); // Aqui você define o precoPago como o total do pedido
        }

        Pedido salvo = pedidoRepository.save(pedido);
        return mapToResponseDTO(salvo);
    }



    public List<PedidoResponseDTO> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO buscarPorId(Long id, Long clienteId) {
        Pedido pedido = pedidoRepository.findByIdAndClienteId(id, clienteId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado ou acesso negado"));
        return mapToResponseDTO(pedido);
    }

    public void cancelar(Long id, Long clienteId) {
        Pedido pedido = pedidoRepository.findByIdAndClienteId(id, clienteId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado ou acesso negado"));
        pedido.setCancelado(true);
        pedidoRepository.save(pedido);
    }

    public List<PedidoResponseDTO> historico(Long clienteId) {
        return pedidoRepository.findByClienteIdAndCanceladoTrue(clienteId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private PedidoResponseDTO mapToResponseDTO(Pedido pedido) {
        PedidoResponseDTO response = new PedidoResponseDTO();
        response.setId(pedido.getId());
        response.setClienteId(pedido.getCliente().getId());
        response.setTotal(pedido.getTotal());
        response.setItens(pedido.getItens().stream().map(i -> {
            PedidoResponseDTO.ItemDTO itemDto = new PedidoResponseDTO.ItemDTO();
            itemDto.setProdutoId(i.getProduto().getId());
            itemDto.setNomeProduto(i.getNomeProduto());
            itemDto.setQuantidade(i.getQuantidade());
            itemDto.setPrecoUnitario(i.getPrecoUnitario());
            itemDto.setDescountPrice(i.getDescountPrice()); // Preço com desconto
            itemDto.setPrecoPago(pedido.getTotal()); // Preço total pago pelo pedido
            return itemDto;
        }).collect(Collectors.toList()));
        return response;
    }
}