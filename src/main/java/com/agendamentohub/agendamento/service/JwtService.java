package com.agendamentohub.agendamento.service;

import com.agendamentohub.agendamento.model.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final String SECRET = "f9edc7463b36bebab6701627571f4c065b9b6d5d41f0c69218328a4f5f5566bbdf6a7ffcb817296e9901b488b8dd9a947a9bf66a0d899ec24b401a938f852f4e07f6ebeb86778fd225f34a94e3512c8271e5f1d7dfe5b1f94c97b632abe04eee15c99cacc483c9957d56d91a461356e0675202b9d4e61265211cb0adf1b4b166b9442b16d6265ae6484eba0d23c63c8eb880ff93ae670c6b52b7a5ad4990f542e24d55fe3d77b4f1ac9de6b5492491ff89eb6a7cbaea53739aa93b813cef38257d58a2a6db7124b506ec6196de5201569e963e046086d92cbeee6d0afbca9235de08386a13b601aa28c612d842e88f162dedcb0fcacbc6677be3d3d83e7f2c75";
    private final long EXPIRATION_MS = 3600000; // 1 hora

    public String generateToken(String email, Set<Role> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles.stream()
                .map(Role::name) // Converte para String
                .toList()); // Adiciona como lista de strings

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith( SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Set<Role> extractRoles(String token) {
        List<String> roleNames = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .get("roles", List.class); // Extrai a lista de roles do token

        return roleNames.stream()
                .map(Role::valueOf) // Converte para o Enum Role
                .collect(Collectors.toSet());
    }

    public boolean isTokenValid(String token, String userEmail) {
        String username = extractUsername(token);
        return (username.equals(userEmail) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
