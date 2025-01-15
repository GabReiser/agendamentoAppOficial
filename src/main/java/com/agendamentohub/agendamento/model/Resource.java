package com.agendamentohub.agendamento.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "resources")
public class Resource {

    @Id
    private String id;

    private String name;      // Ex.: "Sala A", "Consultório 1"
    private String location;  // Ex.: "2º andar"
    private int capacity;     // Quantidade máxima de pessoas, se fizer sentido
}
