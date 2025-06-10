package org.ecommerce.ecommerceapi.modules.cliente.useCases;

import org.ecommerce.ecommerceapi.modules.cliente.dto.ProfileClienteResponseDTO;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileClienteUseCase {

    @Autowired
    private ClienteRepository clienteRepository;

    public ProfileClienteResponseDTO execute(Long clienteId) {
        var cliente = this.clienteRepository.findById(clienteId)
                .orElseThrow(() -> {
                    throw new RuntimeException("Cliente não encontrado");
                });

        var clienteDTO = ProfileClienteResponseDTO.builder()
                .nome(cliente.getNome())
                .username(cliente.getUsername())
                .email(cliente.getEmail())
                .telefone(cliente.getTelefone())
                .endereco(cliente.getEndereco())
                .cidade(cliente.getCidade())
                .estado(cliente.getEstado())
                .cep(cliente.getCep())
                .build();

        return clienteDTO;
    }
}


