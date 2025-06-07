package org.ecommerce.ecommerceapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.swagger-url}")
    private String swaggerServerUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT token obtido no login")
                        ))
                .info(new Info()
                        .title("E-commerce API")
                        .description("""
                            API para sistema de e-commerce com as seguintes funcionalidades:
                            - Autenticação e autorização com JWT
                            - Gerenciamento de clientes
                            - Gerenciamento de produtos
                            - Gerenciamento de pedidos
                            - Processamento de pagamentos
                            """)
                        .version("1.0")
                        .contact(new Contact()
                                .name("E-commerce Team")
                                .email("contato@ecommerce.com")
                                .url("https://github.com/seu-usuario/ecommerce-api"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url(swaggerServerUrl)
                                .description("Servidor Configurado")
                ))
                .tags(List.of(
                        new Tag().name("Auth").description("Operações de autenticação"),
                        new Tag().name("Clientes").description("Gerenciamento de clientes"),
                        new Tag().name("Produtos").description("Gerenciamento de produtos"),
                        new Tag().name("Pedidos").description("Gerenciamento de pedidos"),
                        new Tag().name("Pagamentos").description("Processamento de pagamentos")
                ));
    }
}
