package com.agendamento.agendamentoApp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aula {

    @Id
    private String id;
    private String alunoid;
    private String professorId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataHoraFim;
    private String description;
}
