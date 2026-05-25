package org.ecommerce.ecommerceapi.modules.client.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String cep;
}
