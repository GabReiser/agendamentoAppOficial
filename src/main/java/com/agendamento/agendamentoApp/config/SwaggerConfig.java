package com.agendamento.agendamentoApp.config;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.PathSelectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30) // Use OAS_30 for OpenAPI 3.0
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.agendamento.agendamentoApp.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
