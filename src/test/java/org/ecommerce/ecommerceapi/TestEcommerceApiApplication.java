package org.ecommerce.ecommerceapi;

import org.springframework.boot.SpringApplication;

public class TestEcommerceApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(EcommerceApiApplication::main).with(org.ecommerce.ecommerceapi.TestcontainersConfiguration.class).run(args);
    }

}
