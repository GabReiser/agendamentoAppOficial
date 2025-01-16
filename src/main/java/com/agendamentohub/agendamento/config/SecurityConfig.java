package com.agendamentohub.agendamento.config;

import com.agendamentohub.agendamento.jwt.JwtAuthenticationFilter;
import com.agendamentohub.agendamento.security.CustomOidcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http,
            CustomOidcUserService customOidcUserService, // Custom UserInfo Service para OAuth2
            OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler // Manipula sucesso OAuth2
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Desativa CSRF para APIs
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT é stateless
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/oauth2/**").permitAll() // Permite endpoints públicos
                        .anyRequest().authenticated() // Protege outros endpoints
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(u -> u.oidcUserService(customOidcUserService)) // Configura UserInfo
                        .successHandler(oAuth2AuthenticationSuccessHandler) // Configura handler de sucesso
                        .loginPage("/auth/login/google") // Define endpoint customizado para login
                )
                .formLogin(form -> form.disable()); // Desativa login via formulário

        // Adiciona o filtro JWT antes do UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }
}
