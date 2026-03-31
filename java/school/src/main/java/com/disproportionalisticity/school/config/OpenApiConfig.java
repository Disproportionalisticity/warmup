package com.disproportionalisticity.school.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI schoolOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("School Management API")
                        .description("API for managing students and classes")
                        .version("v1.0.0"));
    }
}