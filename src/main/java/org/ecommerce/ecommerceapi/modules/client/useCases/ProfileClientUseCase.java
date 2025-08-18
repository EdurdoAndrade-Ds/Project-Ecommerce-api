package org.ecommerce.ecommerceapi.modules.client.useCases;

import org.ecommerce.ecommerceapi.exceptions.ClientUnauthorizedException;
import org.ecommerce.ecommerceapi.modules.client.dto.ProfileClientResponseDTO;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileClientUseCase {

    @Autowired
    ClientRepository clientRepository;

    public ProfileClientResponseDTO execute(Long clienteId) {
        var client = this.clientRepository.findById(clienteId)
                .orElseThrow(() -> new ClientUnauthorizedException("Cliente nao encontrado"));

        return ProfileClientResponseDTO.builder()
                .name(client.getName())
                .username(client.getUsername())
                .email(client.getEmail())
                .phone(client.getPhone())
                .address(client.getAddress())
                .city(client.getCity())
                .state(client.getState())
                .cep(client.getCep())
                .build();
    }
}


