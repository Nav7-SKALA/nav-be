package com.skala.nav7.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("NAV7 API")
                        .description("스칼라 프로젝트를 위한 스웨거입니다.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("NAV7")));
    }
}
