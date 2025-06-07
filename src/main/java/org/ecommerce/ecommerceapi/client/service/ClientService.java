package org.ecommerce.ecommerceapi.client.service;

import lombok.extern.slf4j.Slf4j;
import org.ecommerce.ecommerceapi.client.dto.ClientRequestDTO;
import org.ecommerce.ecommerceapi.client.dto.ClientResponseDTO;
import org.ecommerce.ecommerceapi.client.model.Client;
import org.ecommerce.ecommerceapi.client.repository.ClientRepository;
import org.ecommerce.ecommerceapi.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    
    @Transactional
    public ClientResponseDTO saveClient(ClientRequestDTO dto) {
            log.info("Salvando novo cliente: {}", dto.getEmail());
        
            validateEmailExists(dto.getEmail());
        
            Client client = new Client();
            client.setName(dto.getName());
            client.setEmail(dto.getEmail());
            client.setTelefone(dto.getTelefone());
            client.setSenha(passwordEncoder.encode(dto.getSenha()));
        
            Client saved = clientRepository.save(client);
            log.info("Cliente salvo com sucesso: {}", saved.getId());
        
            return toResponseDTO(saved);
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

    @Transactional
    public ClientResponseDTO updateClient(String identifier, ClientRequestDTO dto, boolean isEmail) {
        Client client = findClient(identifier, isEmail);
        validateUpdate(client, dto);
        updateClientFields(client, dto);
        
        Client updated = clientRepository.save(client);
        log.info("Cliente atualizado: {}", updated.getId());
        
        return toResponseDTO(updated);
    }

    @Transactional
    public void deleteClient(String identifier, boolean isEmail) {
        Client client = findClient(identifier, isEmail);
        clientRepository.delete(client);
        log.info("Cliente removido: {}", client.getId());
    }

    public ClientResponseDTO searchForEmail(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com email " + email));
        return toResponseDTO(client);
    }

    private Client findClient(String identifier, boolean isEmail) {
        return isEmail
            ? clientRepository.findByEmail(identifier)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com email " + identifier))
            : clientRepository.findById(Long.parseLong(identifier))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + identifier));
    }

    private void validateEmailExists(String email) {
        if (clientRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
    }

    private void validateUpdate(Client client, ClientRequestDTO dto) {
        if (dto.getEmail() != null && !dto.getEmail().equals(client.getEmail())) {
            throw new IllegalArgumentException("Não é permitido alterar o email");
        }
    }

    private void updateClientFields(Client client, ClientRequestDTO dto) {
        if (dto.getName() != null) {
            client.setName(dto.getName());
        }
        if (dto.getTelefone() != null) {
            client.setTelefone(dto.getTelefone());
        }
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            client.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
    }

    private ClientResponseDTO toResponseDTO(Client client) {
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setTelefone(client.getTelefone());
        return dto;
    }
}