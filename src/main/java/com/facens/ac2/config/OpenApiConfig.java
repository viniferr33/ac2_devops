package com.facens.ac2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI setup() {
        return new OpenAPI().info(
                new Info().title("Ac2 API")
                        .description("Test")
                        .version("1.0")
        );
    }

}
