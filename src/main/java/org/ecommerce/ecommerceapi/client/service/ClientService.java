package org.ecommerce.ecommerceapi.client.service;

import org.ecommerce.ecommerceapi.client.dto.ClientRequestDTO;
import org.ecommerce.ecommerceapi.client.dto.ClientResponseDTO;
import org.ecommerce.ecommerceapi.client.model.Client;
import org.ecommerce.ecommerceapi.client.repository.ClientRepository;
import org.ecommerce.ecommerceapi.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Salvar cliente a partir do DTO de request e retorna DTO de response
    public ClientResponseDTO saveClient(ClientRequestDTO dto) {
        Client client = new Client();
        client.setName(dto.name());
        client.setEmail(dto.email());
        client.setTelefone(dto.telefone());
        client = clientRepository.save(client);
        return toResponseDTO(client);
    }

    // Listar todos os clientes e converter para DTOs de response
    public List<ClientResponseDTO> listAllClient() {
        return clientRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Buscar cliente por ID, lançar exceção se não encontrar
    public ClientResponseDTO searchForIdClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + id));
        return toResponseDTO(client);
    }

    // Atualizar cliente existente com dados do DTO de request e retorna DTO de response
    public ClientResponseDTO updateClient(Long id, ClientRequestDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + id));
        client.setName(dto.name());
        client.setEmail(dto.email());
        client.setTelefone(dto.telefone());
        client = clientRepository.save(client);
        return toResponseDTO(client);
    }

    // Deletar cliente pelo ID
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com id " + id);
        }
        clientRepository.deleteById(id);
    }

    // Buscar clientes por email contendo string, e retornar DTOs
    public List<ClientResponseDTO> searchForEmail(String email) {
        return clientRepository.findByEmailContaining(email)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Método auxiliar para converter Client em ClientResponseDTO
    private ClientResponseDTO toResponseDTO(Client client) {
        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getTelefone()
        );
    }
}
