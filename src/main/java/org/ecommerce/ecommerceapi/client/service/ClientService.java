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
    private PasswordEncoder passwordEncoder; // Injeção do encoder

    public ClientResponseDTO saveClient(ClientRequestDTO dto) {
        Client client = new Client();
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setTelefone(dto.getTelefone());
        // Criptografando a senha antes de salvar
        client.setSenha(passwordEncoder.encode(dto.getSenha()));
        client = clientRepository.save(client);
        return toResponseDTO(client);
    }

    public List<ClientResponseDTO> listAllClient() {
        return clientRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ClientResponseDTO searchForIdClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + id));
        return toResponseDTO(client);
    }

    public ClientResponseDTO updateClient(Long id, ClientRequestDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + id));
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setTelefone(dto.getTelefone());
        // Atualiza a senha criptografando-a (se quiser permitir atualização da senha)
        client.setSenha(passwordEncoder.encode(dto.getSenha()));
        client = clientRepository.save(client);
        return toResponseDTO(client);
    }

    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com id " + id);
        }
        clientRepository.deleteById(id);
    }

    public List<ClientResponseDTO> searchForEmail(String email) {
        return clientRepository.findByEmailContaining(email)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private ClientResponseDTO toResponseDTO(Client client) {
        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getTelefone()
                // NÃO inclui a senha no response DTO por segurança
        );
    }
}
