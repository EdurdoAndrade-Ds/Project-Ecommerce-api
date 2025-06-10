package org.ecommerce.ecommerceapi;

import org.springframework.boot.SpringApplication;
import org.ecommerce.ecommerceapi.EcommerceApiApplication;
import org.ecommerce.ecommerceapi.TestcontainersConfiguration;

public class TestEcommerceApiApplication {

    public static void main(String[] args) {
        SpringApplication
            .from(EcommerceApiApplication::main)
            .with(TestcontainersConfiguration.class)
            .run(args);
    }
}
