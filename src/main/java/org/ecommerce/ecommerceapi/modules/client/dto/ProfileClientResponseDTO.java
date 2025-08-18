package org.ecommerce.ecommerceapi.modules.client.dto;

import java.util.Objects;

import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
public class ProfileClientResponseDTO {
    private String name;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String cep;

}
