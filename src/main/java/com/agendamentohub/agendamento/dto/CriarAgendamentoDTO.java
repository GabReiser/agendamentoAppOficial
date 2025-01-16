package com.agendamentohub.agendamento.dto;

import java.time.LocalDateTime;

public class CriarAgendamentoDTO {

    private LocalDateTime data;
    private String descricao;

    // Getters e Setters
    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
