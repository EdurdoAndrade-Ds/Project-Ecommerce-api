package org.ecommerce.ecommerceapi.modules.cliente.useCases;

import org.ecommerce.ecommerceapi.modules.cliente.dto.UpdateClienteDTO;
import org.ecommerce.ecommerceapi.modules.cliente.entities.ClienteEntity;
import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdateClienteUseCase {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteEntity execute(Long clienteId, UpdateClienteDTO updateClienteDTO) {
        ClienteEntity cliente = this.clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        if (updateClienteDTO.getUsername() != null || updateClienteDTO.getEmail() != null) {
            var existingCliente = this.clienteRepository.findByUsernameOrEmail(
                    updateClienteDTO.getUsername() != null ? updateClienteDTO.getUsername() : cliente.getUsername(),
                    updateClienteDTO.getEmail() != null ? updateClienteDTO.getEmail() : cliente.getEmail()
            );

            if (existingCliente.isPresent() && !existingCliente.get().getId().equals(clienteId)) {
                throw new RuntimeException("Cliente já existe");
            }
        }

        if (updateClienteDTO.getNome() != null) {
            cliente.setNome(updateClienteDTO.getNome());
        }

        if (updateClienteDTO.getUsername() != null) {
            cliente.setUsername(updateClienteDTO.getUsername());
        }

        if (updateClienteDTO.getEmail() != null) {
            cliente.setEmail(updateClienteDTO.getEmail());
        }

        if (updateClienteDTO.getTelefone() != null) {
            cliente.setTelefone(updateClienteDTO.getTelefone());
        }

        if (updateClienteDTO.getEndereco() != null) {
            cliente.setEndereco(updateClienteDTO.getEndereco());
        }

        if (updateClienteDTO.getCidade() != null) {
            cliente.setCidade(updateClienteDTO.getCidade());
        }

        if (updateClienteDTO.getEstado() != null) {
            cliente.setEstado(updateClienteDTO.getEstado());
        }

        if (updateClienteDTO.getCep() != null) {
            cliente.setCep(updateClienteDTO.getCep());
        }

        return this.clienteRepository.save(cliente);
    }
}

