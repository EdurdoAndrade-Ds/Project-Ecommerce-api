package org.ecommerce.ecommerceapi.modules.client.dto;

import lombok.*;

@Getter @Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AuthClientDTO {


    private String username;
    private String password;
}