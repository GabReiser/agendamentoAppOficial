package com.agendamento.agendamentoApp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {
    @Id
    private String id;
    private String name;
    private String contact;
}
