package com.agendamentohub.agendamento.dto;

import java.time.LocalDateTime;

public class AtualizarAgendamentoDTO {
    private LocalDateTime novaData;
    private String novaDescricao;

    // Getters e Setters
    public LocalDateTime getNovaData() {
        return novaData;
    }

    public void setNovaData(LocalDateTime novaData) {
        this.novaData = novaData;
    }

    public String getNovaDescricao() {
        return novaDescricao;
    }

    public void setNovaDescricao(String novaDescricao) {
        this.novaDescricao = novaDescricao;
    }
}
