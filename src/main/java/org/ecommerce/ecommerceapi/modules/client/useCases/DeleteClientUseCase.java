package org.ecommerce.ecommerceapi.modules.client.useCases;

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
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        var passwordMatches = this.passwordEncoder.matches(password, client.getPassword());

        if (!passwordMatches) {
            throw new RuntimeException("Senha incorreta");
        }

        client.setActive(false);
        this.clientRepository.save(client);
    }
}


