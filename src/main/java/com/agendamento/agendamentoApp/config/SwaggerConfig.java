package com.agendamento.agendamentoApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30)  // OpenAPI Specification 3.0
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.exemplo.sistemaagendamento"))  // Adjust the package to scan for APIs
                .paths(PathSelectors.any())
                .build();
    }
}
