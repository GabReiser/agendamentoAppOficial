package com.agendamentohub.agendamento.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "reservations")
public class Reservation {

    @Id
    private String id;

    private String userId;
    private String resourceId;

    private LocalDate date;
    private LocalDate startTime;
    private LocalDate endTime;

    private ReservationStatus status;
}
