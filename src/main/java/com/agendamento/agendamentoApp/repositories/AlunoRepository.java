package com.agendamento.agendamentoApp.repositories;

import com.agendamento.agendamentoApp.models.Aluno;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface AlunoRepository extends MongoRepository<Aluno, String> {
}
