package org.ecommerce.ecommerceapi.modules.client.useCases;

import org.ecommerce.ecommerceapi.exceptions.ClientConflictException;
import org.ecommerce.ecommerceapi.modules.client.dto.ClientResponseDTO;
import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateClientUseCase {

    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;

    public ClientResponseDTO execute(ClientEntity clientEntity) {
        var client = this.clientRepository.findByUsernameOrEmail(clientEntity.getUsername(), clientEntity.getEmail());

        if (client.isPresent()) {
            throw new ClientConflictException("Cliente já existe");
        }

        var senha = passwordEncoder.encode(clientEntity.getPassword());
        clientEntity.setPassword(senha);
        clientEntity.setActive(true);

        var clientCreated = this.clientRepository.save(clientEntity);
        return ClientResponseDTO.builder()
                .id(clientCreated.getId())
                .name(clientCreated.getName())
                .username(clientCreated.getUsername())
                .email(clientCreated.getEmail())
                .phone(clientCreated.getPhone())
                .address(clientCreated.getAddress())
                .city(clientCreated.getCity())
                .state(clientCreated.getState())
                .cep(clientCreated.getCep())
                .build();
    }
}

