package org.ecommerce.ecommerceapi.modules.cliente.useCases;

import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateClienteUseCase {

    @Autowired ClienteRepository clienteRepository;

    @Autowired PasswordEncoder passwordEncoder;

    public ClienteEntity execute(ClienteEntity clienteEntity) {
        var cliente = this.clienteRepository.findByUsernameOrEmail(clienteEntity.getUsername(), clienteEntity.getEmail());

        if (cliente.isPresent()) {
            throw new RuntimeException("Cliente já existe");
        }

        var senha = passwordEncoder.encode(clienteEntity.getSenha());
        clienteEntity.setSenha(senha);
        clienteEntity.setAtivo(true);

        var clienteCreated = this.clienteRepository.save(clienteEntity);
        return clienteCreated;
    }
}

