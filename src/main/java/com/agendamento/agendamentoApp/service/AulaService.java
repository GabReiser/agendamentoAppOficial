package com.agendamento.agendamentoApp.service;

import com.agendamento.agendamentoApp.models.Aula;
import com.agendamento.agendamentoApp.repositories.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    public boolean isHorarioDisponivel(LocalDateTime inicio, LocalDateTime fim) {
        List<Aula> aulas = aulaRepository.findAll();
        for (Aula aula : aulas) {
            if (inicio.isBefore(aula.getDataHoraFim()) && fim.isAfter(aula.getDataInicio())) {
                return false; // Horário solicitado conflita com outro já agendado
            }
        }
        return true; // Nenhum conflito, horário está disponível
    }
    public Aula agendarAula(Aula aula) {
        if (isHorarioDisponivel(aula.getDataInicio(), aula.getDataHoraFim())) {
            return aulaRepository.save(aula);
        }
        throw new IllegalStateException("Horário não disponível para agendamento.");
    }
    public Optional<Aula> cancelarAula(String id) {
        Optional<Aula> aula = aulaRepository.findById(id);
        aula.ifPresent(a -> aulaRepository.delete(a));
        return aula;
    }
    public List<Aula> listarTodasAulas() {
        return aulaRepository.findAll();
    }

}
