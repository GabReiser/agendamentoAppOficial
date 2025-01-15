package com.agendamentohub.agendamento.service;

import com.agendamentohub.agendamento.model.Role;
import com.agendamentohub.agendamento.model.User;
import com.agendamentohub.agendamento.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Busca usuário por email. Se não existir, retorna Optional vazio.
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Cria um novo usuário no MongoDB com dados básicos.
     */
    public User createUser(String name, String email, String phoneNumber) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);

        // se quiser atribuir role padrão:
        user.setRoles(Set.of(Role.USER));

        return userRepository.save(user);
    }

    /**
     * Cria um usuário caso ele não exista; se já existir, retorna o existente.
     */
    public User createUserIfNotExists(String name, String email, String phoneNumber) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    // se não existe, cria
                    return createUser(name, email, phoneNumber);
                });
    }


}
