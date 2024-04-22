package com.agendamento.agendamentoApp.repositories;

import com.agendamento.agendamentoApp.models.Aluno;
import com.agendamento.agendamentoApp.models.Aula;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AulaRepository extends MongoRepository<Aula, String> {
}
