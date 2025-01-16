package com.agendamentohub.agendamento.dto;

import com.agendamentohub.agendamento.model.StatusAgendamento;

import java.time.LocalDateTime;

public class AgendamentoResponseDTO {
    private String id;
    private LocalDateTime data;
    private StatusAgendamento status;
    private String descricao;

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
