package org.ecommerce.ecommerceapi.modules.client.useCases;

import org.ecommerce.ecommerceapi.exceptions.BusinessException;
import org.ecommerce.ecommerceapi.exceptions.ClientNotFoundException;
import org.ecommerce.ecommerceapi.modules.client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DeleteClientUseCase {
    @Autowired
    ClientRepository clientRepository;

    @Autowired PasswordEncoder passwordEncoder;

    public void execute(Long clienteId, String password) {
        var client = this.clientRepository.findById(clienteId)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado"));

        var passwordMatches = this.passwordEncoder.matches(password, client.getPassword());

        if (!passwordMatches) {
            throw new BusinessException("Senha incorreta");
        }

        client.setActive(false);
        this.clientRepository.save(client);
    }
}


