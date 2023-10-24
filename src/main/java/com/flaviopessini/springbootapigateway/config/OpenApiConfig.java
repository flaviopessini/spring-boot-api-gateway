package com.flaviopessini.springbootapigateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Restful Api with Java 17 and Spring Boot 3")
                .version("v1")
                .description("Restful Api with Java 17 and Spring Boot 3")
                .termsOfService("Blábláblá")
                .license(new License().name("Apache 2.0")));
    }
}
