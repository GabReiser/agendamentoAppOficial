package com.agendamentohub.agendamento.repository;

import com.agendamentohub.agendamento.model.Agendamentos;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends MongoRepository<Agendamentos, String> {
    // Método para verificar agendamentos que colidem com o horário desejado
    List<Agendamentos> findByDataBetween(LocalDateTime start, LocalDateTime end);

    // Método para buscar agendamentos por usuário
    List<Agendamentos> findByUsuarioId(String usuarioId);
}
