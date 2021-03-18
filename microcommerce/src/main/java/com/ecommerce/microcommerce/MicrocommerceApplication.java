package com.ecommerce.microcommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MicrocommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrocommerceApplication.class, args);
	}

}
