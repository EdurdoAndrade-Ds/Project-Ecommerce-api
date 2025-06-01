package org.ecommerce.ecommerceapi.client.service;

import org.ecommerce.ecommerceapi.client.dto.ClientRequestDTO;
import org.ecommerce.ecommerceapi.client.dto.ClientResponseDTO;
import org.ecommerce.ecommerceapi.client.model.Client;
import org.ecommerce.ecommerceapi.client.repository.ClientRepository;
import org.ecommerce.ecommerceapi.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Cria um novo cliente
    public ClientResponseDTO saveClient(ClientRequestDTO dto) {
        Client client = new Client();
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setTelefone(dto.getTelefone());
        client.setSenha(passwordEncoder.encode(dto.getSenha()));
        Client saved = clientRepository.save(client);
        return toResponseDTO(saved);
    }

    // Lista todos os clientes
    public List<ClientResponseDTO> listAllClient() {
        return clientRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Busca por id
    public ClientResponseDTO searchForIdClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + id));
        return toResponseDTO(client);
    }

    // Atualiza cliente
    public ClientResponseDTO updateClient(Long id, ClientRequestDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + id));
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setTelefone(dto.getTelefone());
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            client.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        Client updated = clientRepository.save(client);
        return toResponseDTO(updated);
    }

    // Remove cliente
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + id));
        clientRepository.delete(client);
    }

    // Busca clientes por email
    public List<ClientResponseDTO> searchForEmail(String email) {
        return clientRepository.findAll().stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Conversão de entidade para DTO de resposta
    private ClientResponseDTO toResponseDTO(Client client) {
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setTelefone(client.getTelefone());
        // Não inclua a senha no DTO de resposta!
        return dto;
    }
}