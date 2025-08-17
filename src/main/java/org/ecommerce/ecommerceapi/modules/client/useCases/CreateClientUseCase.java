package org.ecommerce.ecommerceapi.modules.client.useCases;

import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateClientUseCase {

    @Autowired
    ClientRepository clientRepository;

    @Autowired PasswordEncoder passwordEncoder;

    public ClientEntity execute(ClientEntity clientEntity) {
        var client = this.clientRepository.findByUsernameOrEmail(clientEntity.getUsername(), clientEntity.getEmail());

        if (client.isPresent()) {
            throw new RuntimeException("Cliente já existe");
        }

        var senha = passwordEncoder.encode(clientEntity.getPassword());
        clientEntity.setPassword(senha);
        clientEntity.setActive(true);

        var clientCreated = this.clientRepository.save(clientEntity);
        return clientCreated;
    }
}

