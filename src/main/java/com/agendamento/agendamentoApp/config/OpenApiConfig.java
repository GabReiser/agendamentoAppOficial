package com.agendamento.agendamentoApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Agendamento API")
                        .description("Documentação da API de Agendamento usando SpringDoc OpenAPI 3")
                        .version("v0.0.1"));
    }
}
