package org.ecommerce.ecommerceapi.modules.client.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthClientDTO {


    private String username;
    private String password;
}