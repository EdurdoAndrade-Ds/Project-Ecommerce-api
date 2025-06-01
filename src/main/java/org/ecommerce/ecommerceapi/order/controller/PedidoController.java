package org.ecommerce.ecommerceapi.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ecommerce.ecommerceapi.order.model.Pedido;
import org.ecommerce.ecommerceapi.order.repository.PedidoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Operation(summary = "Lista todos os pedidos")
    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Operation(summary = "Busca um pedido pelo ID")
    @GetMapping("/{id}")
    public Optional<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id);
    }

    @Operation(summary = "Cria um novo pedido")
    @PostMapping
    public Pedido salvar(@RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Operation(summary = "Remove um pedido pelo ID")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        pedidoRepository.deleteById(id);
    }
}