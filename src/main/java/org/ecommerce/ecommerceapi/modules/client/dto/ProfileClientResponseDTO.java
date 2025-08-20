package org.ecommerce.ecommerceapi.modules.client.dto;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
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
