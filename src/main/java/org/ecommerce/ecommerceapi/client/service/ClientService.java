package org.ecommerce.ecommerceapi.client.service;

import java.util.List;

import org.ecommerce.ecommerceapi.client.model.Client;
import org.ecommerce.ecommerceapi.client.repository.ClientRepository;
import org.ecommerce.ecommerceapi.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // salvar cliente
    public Client saveClient(Client cliente) {
        return clientRepository.save(cliente);
    }

    // Listar todos clientes
    public List<Client> listAllClient() {
        return clientRepository.findAll();
    }

    // buscar por id
    public Client searchForIdClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + id));
    }

    // atualizar cliente
    public Client updateClient(Long id, Client clienteUpdated) {
        Client cliente = searchForIdClient(id);
        cliente.setName(clienteUpdated.getName());
        cliente.setEmail(clienteUpdated.getEmail());
        cliente.setTelefone(clienteUpdated.getTelefone());
        return clientRepository.save(cliente);
    }

    // deletar cliente
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    // buscar clientes por email
    public List<Client> searchForEmail(String email) {
        return clientRepository.findByEmailContaining(email);
    }

    
}