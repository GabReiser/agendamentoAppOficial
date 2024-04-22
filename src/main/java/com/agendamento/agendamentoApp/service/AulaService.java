package com.agendamento.agendamentoApp.service;

import com.agendamento.agendamentoApp.models.Aula;
import com.agendamento.agendamentoApp.repositories.AulaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;
    @Autowired
    ObjectMapper objectMapper;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
    public boolean isHorarioDisponivel(String inicioStr, String dataHoraFim) {
        LocalDateTime inicio = LocalDateTime.parse(inicioStr, formatter);
        LocalDateTime fim = inicio.plusMinutes(60); // Duração da aula é de 60 minutos

        List<Aula> aulas = aulaRepository.findAll();
        for (Aula aula : aulas) {
            LocalDateTime aulaInicio = LocalDateTime.parse(aula.getDataInicio(), formatter);
            LocalDateTime aulaFim = aulaInicio.plusMinutes(60);
            if (inicio.isBefore(aulaFim) && fim.isAfter(aulaInicio)) {
                return false; // Horário solicitado conflita com outro já agendado
            }
        }
        return true; // Nenhum conflito, horário está disponível
    }
    public Aula agendarAula(String aula) throws JsonProcessingException {
        Aula aulaObject = objectMapper.readValue(aula, Aula.class);
        if (isHorarioDisponivel(aulaObject.getDataInicio(), aulaObject.getDataHoraFim())) {
            return aulaRepository.save(aulaObject);
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
