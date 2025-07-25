package org.ecommerce.ecommerceapi.modules.cliente.entities;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo [nome] é obrigatório")
    private String nome;

    @NotBlank(message = "O campo [username] é obrigatório")
    @Pattern(regexp="\\S+", message = "O campo [username] não deve conter espaço")
    private String username;

    @Email(message = "O campo [email] deve conter um email válido")
    @NotBlank(message = "O campo [email] é obrigatório")
    private String email;

    @Length(min=8, max=100, message= "A senha deve ter entre 8 e 100 caracteres")
    @NotBlank(message = "O campo [senha] é obrigatório")
    private String senha;

    private String telefone;

    private String endereco;

    private String cidade;

    private String estado;

    private String cep;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean ativo = true;

    public Long getId() {
        return id;
    }
}
