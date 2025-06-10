package org.ecommerce.ecommerceapi.modules.cliente.useCases;

import org.ecommerce.ecommerceapi.modules.cliente.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DeleteClienteUseCase {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void execute(Long clienteId, String senha) {
        var cliente = this.clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        var passwordMatches = this.passwordEncoder.matches(senha, cliente.getSenha());

        if (!passwordMatches) {
            throw new RuntimeException("Senha incorreta");
        }

        cliente.setAtivo(false);
        this.clienteRepository.save(cliente);
    }
}


