package com.agendamentohub.agendamento.controller;

import com.agendamentohub.agendamento.dto.LoginDTO;
import com.agendamentohub.agendamento.dto.RegisterDTO;
import com.agendamentohub.agendamento.model.User;
import com.agendamentohub.agendamento.service.JwtService;
import com.agendamentohub.agendamento.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserService userService,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDto) {
        // Verifica se já existe esse email
        if (userService.findByEmail(registerDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já está em uso!");
        }
        // Cria o usuário criptografando a senha
        userService.createUser(
                registerDto.getName(),
                registerDto.getEmail(),
                registerDto.getPhoneNumber(),
                registerDto.getPassword() // passamos a senha crua aqui
        );
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        // Busca o usuário pelo email
        Optional<User> userOptional = userService.findByEmail(loginDTO.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }

        User user = userOptional.get();

        // Verifica se a senha está correta
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }

        // Gera o token JWT
        String jwt = jwtService.generateToken(user.getEmail());

        // Retorna o token em JSON
        return ResponseEntity.ok().body("{\"token\":\"" + jwt + "\"}");
    }
    @GetMapping("/login/google")
    public void redirectToGoogleLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }
}
