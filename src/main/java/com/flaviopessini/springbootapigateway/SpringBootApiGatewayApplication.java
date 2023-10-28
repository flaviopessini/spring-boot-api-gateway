package com.flaviopessini.springbootapigateway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringBootApiGatewayApplication {

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Map<String, PasswordEncoder> encoders = new HashMap<>();

            var pbkdf2 = new Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
            encoders.put("pbkdf2", pbkdf2);
            DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
            passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2);

            final var result = passwordEncoder.encode("admin123");
            System.out.println("My hash " + result);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApiGatewayApplication.class, args);
    }

}
