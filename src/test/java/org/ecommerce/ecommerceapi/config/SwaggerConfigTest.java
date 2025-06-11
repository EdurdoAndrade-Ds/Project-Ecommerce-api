package org.ecommerce.ecommerceapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    @Test
    void customOpenAPI_ShouldReturnConfiguredOpenAPI() {
        SwaggerConfig swaggerConfig = new SwaggerConfig();
        OpenAPI openAPI = swaggerConfig.customOpenAPI();

        // Verifica título, versão e descrição
        Info info = openAPI.getInfo();
        assertNotNull(info);
        assertEquals("API de E-commerce", info.getTitle());
        assertEquals("1.0", info.getVersion());
        assertTrue(info.getDescription().contains("clientes"));

        // Verifica licença
        assertNotNull(info.getLicense());
        assertEquals("Apache 2.0", info.getLicense().getName());

        // Verifica se existe o esquema de segurança "Bearer Authentication"
        final String securitySchemeName = "Bearer Authentication";
        assertNotNull(openAPI.getComponents());
        SecurityScheme securityScheme = openAPI.getComponents().getSecuritySchemes().get(securitySchemeName);
        assertNotNull(securityScheme);
        assertEquals(SecurityScheme.Type.HTTP, securityScheme.getType());
        assertEquals("bearer", securityScheme.getScheme());
        assertEquals("JWT", securityScheme.getBearerFormat());

        // Verifica se a lista de SecurityRequirements contém o esquema definido
        boolean hasSecurityRequirement = openAPI.getSecurity().stream()
                .anyMatch(req -> req.containsKey(securitySchemeName));
        assertTrue(hasSecurityRequirement);
    }
}
