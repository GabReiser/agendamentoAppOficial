package com.agendamento.agendamentoApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@EnableWebMvc
@SpringBootApplication
public class SistemaAgendamentoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SistemaAgendamentoApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")  // Permite CORS em todas as rotas
				.allowedOriginPatterns("http://localhost:3000", "https://meusite.com")  // Especifique todas as origens permitidas explicitamente
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Métodos HTTP permitidos
				.allowedHeaders("*")  // Cabeçalhos HTTP permitidos
				.allowCredentials(true)  // Permissão para enviar cookies
				.maxAge(3600);  // Tempo máximo que o navegador guarda o resultado da preflight response
	}

}
