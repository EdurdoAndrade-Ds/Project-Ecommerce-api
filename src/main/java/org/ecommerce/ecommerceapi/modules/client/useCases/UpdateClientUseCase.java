package org.ecommerce.ecommerceapi.modules.client.useCases;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ecommerce.ecommerceapi.exceptions.ClientConflictException;
import org.ecommerce.ecommerceapi.exceptions.ClientNotFoundException;
import org.ecommerce.ecommerceapi.modules.client.dto.UpdateClientDTO;
import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.ecommerce.ecommerceapi.modules.client.mapper.ClientMapper;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UpdateClientUseCase {


    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientEntity execute(Long clienteId, UpdateClientDTO upDto) {
        ClientEntity client = this.clientRepository.findById(clienteId)
                .orElseThrow(() -> new ClientNotFoundException("Cliente nao encotrado"));

        String newUsername = upDto.getUsername() != null ? upDto.getUsername() : client.getUsername();
        String newEmail = upDto.getEmail() != null ? upDto.getEmail() : client.getEmail();

        boolean changedUser = !Objects.equals(newUsername, client.getUsername());
        boolean changedMail = !Objects.equals(newEmail, client.getEmail());
        if (changedUser || changedMail) {
            boolean conflict = clientRepository
                    .existsByUsernameOrEmailAndIdNot(newUsername, newEmail, clienteId);
            if (conflict) throw new ClientConflictException();
        }

        clientMapper.updateFromDto(upDto, client);
        return this.clientRepository.save(client);
    }
}

