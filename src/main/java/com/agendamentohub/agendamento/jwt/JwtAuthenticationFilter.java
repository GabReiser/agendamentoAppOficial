package com.agendamentohub.agendamento.jwt;

import com.agendamentohub.agendamento.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1) Pegar cabeçalho Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2) Extrai token
        String jwt = authHeader.substring(7);

        // 3) Valida e extrai e-mail/claims do token
        String userEmail = jwtService.extractUsername(jwt); // parse do token

        // 4) Se tiver usuário e ainda não estiver autenticado
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Verifica se token é válido e carrega dados do usuário
            if (jwtService.isTokenValid(jwt, userEmail)) {
                // Cria objeto de autenticação
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userEmail,  // principal
                                null,       // credentials
                                Collections.emptyList() // roles, se quiser
                        );

                // Seta no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 5) Segue o fluxo
        filterChain.doFilter(request, response);
    }
}
