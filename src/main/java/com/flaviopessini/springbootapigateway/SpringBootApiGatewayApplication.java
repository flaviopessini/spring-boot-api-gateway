package com.flaviopessini.springbootapigateway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootApiGatewayApplication {

	@Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
			System.out.println("PROJETO INICIADO!!!");
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApiGatewayApplication.class, args);
    }

}
