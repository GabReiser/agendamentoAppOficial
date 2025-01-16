package com.agendamentohub.agendamento.service;

import com.agendamentohub.agendamento.dto.AgendamentoResponseDTO;
import com.agendamentohub.agendamento.dto.AlterarStatusDTO;
import com.agendamentohub.agendamento.dto.AtualizarAgendamentoDTO;
import com.agendamentohub.agendamento.dto.CriarAgendamentoDTO;
import com.agendamentohub.agendamento.model.Agendamentos;
import com.agendamentohub.agendamento.model.StatusAgendamento;
import com.agendamentohub.agendamento.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public AgendamentoResponseDTO criarAgendamento(String usuarioId, CriarAgendamentoDTO dto) {
        // Verificar disponibilidade
        LocalDateTime start = dto.getData();
        LocalDateTime end = start.plusMinutes(50); // Sessão de 50 minutos

        List<Agendamentos> conflitos = agendamentoRepository.findByDataBetween(start, end);
        boolean possuiConflitos = conflitos.stream()
                .anyMatch(a -> a.getStatus() != StatusAgendamento.CANCELED);

        if (possuiConflitos) {
            throw new RuntimeException("Horário já está ocupado!");
        }

        // Criar o agendamento
        Agendamentos agendamento = new Agendamentos();
        agendamento.setUsuarioId(usuarioId);
        agendamento.setData(dto.getData());
        agendamento.setDescricao(dto.getDescricao());
        agendamento.setStatus(StatusAgendamento.PENDING);

        Agendamentos salvo = agendamentoRepository.save(agendamento);

        // Retornar DTO
        return toResponseDTO(salvo);
    }

    public AgendamentoResponseDTO atualizarAgendamento(String agendamentoId, AtualizarAgendamentoDTO dto, boolean isAdmin) {
        Optional<Agendamentos> optionalAgendamento = agendamentoRepository.findById(agendamentoId);

        if (optionalAgendamento.isEmpty()) {
            throw new RuntimeException("Agendamento não encontrado!");
        }

        Agendamentos agendamento = optionalAgendamento.get();

        // Verificar se usuário pode atualizar
        if (!isAdmin && agendamento.getData().isBefore(LocalDateTime.now().plusHours(24))) {
            throw new RuntimeException("Somente admins podem atualizar o agendamento com menos de 24 horas de antecedência!");
        }

        // Atualizar campos
        if (dto.getNovaData() != null) {
            agendamento.setData(dto.getNovaData());
        }
        if (dto.getNovaDescricao() != null) {
            agendamento.setDescricao(dto.getNovaDescricao());
        }

        Agendamentos atualizado = agendamentoRepository.save(agendamento);

        return toResponseDTO(atualizado);
    }

    public AgendamentoResponseDTO alterarStatus(String agendamentoId, StatusAgendamento status) {
        Optional<Agendamentos> optionalAgendamento = agendamentoRepository.findById(agendamentoId);

        if (optionalAgendamento.isEmpty()) {
            throw new RuntimeException("Agendamento não encontrado!");
        }

        Agendamentos agendamento = optionalAgendamento.get();
        agendamento.setStatus(status);

        Agendamentos atualizado = agendamentoRepository.save(agendamento);

        return toResponseDTO(atualizado);
    }

    public List<AgendamentoResponseDTO> listarAgendamentos(String usuarioId) {
        List<Agendamentos> agendamentos = agendamentoRepository.findByUsuarioId(usuarioId);

        return agendamentos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private AgendamentoResponseDTO toResponseDTO(Agendamentos agendamento) {
        AgendamentoResponseDTO dto = new AgendamentoResponseDTO();
        dto.setId(agendamento.getId());
        dto.setData(agendamento.getData());
        dto.setStatus(agendamento.getStatus());
        dto.setDescricao(agendamento.getDescricao());
        return dto;
    }
}
