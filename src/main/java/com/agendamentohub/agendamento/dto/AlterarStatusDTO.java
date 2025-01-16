package com.agendamentohub.agendamento.dto;

import com.agendamentohub.agendamento.model.StatusAgendamento;

public class AlterarStatusDTO {

    private StatusAgendamento status;

    // Getters e Setters
    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }
}
