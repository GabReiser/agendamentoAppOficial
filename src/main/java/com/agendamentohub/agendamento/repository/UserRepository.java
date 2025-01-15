package com.agendamentohub.agendamento.repository;

import com.agendamentohub.agendamento.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    Optional<User> findByEmail(String email);
}
