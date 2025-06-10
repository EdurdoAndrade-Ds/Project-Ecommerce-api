package src.test.java.org.ecommerce.ecommerceapi;

import org.ecommerce.ecommerceapi.EcommerceApiApplication;
import org.springframework.boot.SpringApplication;

public class TestEcommerceApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(EcommerceApiApplication::main).with(TestContainersConfiguration.class).run(args);
    }
}
