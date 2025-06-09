package org.ecommerce.ecommerceapi.client.service;

import org.ecommerce.ecommerceapi.client.dto.ClientRequestDTO;
import org.ecommerce.ecommerceapi.client.dto.ClientResponseDTO;
import org.ecommerce.ecommerceapi.client.model.Client;
import org.ecommerce.ecommerceapi.client.model.Role;
import org.ecommerce.ecommerceapi.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Client cadastrar(ClientRequestDTO dto) {
        Client client = new Client();
        client.setNome(dto.getNome());
        client.setEmail(dto.getEmail());
        client.setSenha(passwordEncoder.encode(dto.getSenha()));
        return clientRepository.save(client);
    }

    public ClientResponseDTO criarCliente(ClientRequestDTO dto) {
        if (clientRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado.");
        }

        Client client = new Client();
        client.setNome(dto.getNome());
        client.setEmail(dto.getEmail());
        client.setSenha(passwordEncoder.encode(dto.getSenha()));
        client.setRole(Role.CLIENTE); // ou outro enum conforme sua lógica

        clientRepository.save(client);

        return new ClientResponseDTO(client); // construtor que mapeia entity → DTO
    }

}
