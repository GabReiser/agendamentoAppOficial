package com.agendamento.agendamentoApp.controller;

import com.agendamento.agendamentoApp.models.Aula;
import com.agendamento.agendamentoApp.repositories.AulaRepository;
import com.agendamento.agendamentoApp.service.AulaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOError;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/aulas")
public class AulaController {

    @Autowired
    AulaRepository aulaRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AulaService aulaService;

    @PostMapping("/agendarAula")
    @Operation(summary = "Agendar aula")
    public ResponseEntity<Aula> agendarAula(@RequestBody String aula) throws JsonProcessingException {
        if (aula != null){
            try {
                Aula response = aulaService.agendarAula(aula);
                return new ResponseEntity<Aula>(response, HttpStatus.OK);
            }catch (IOException ex){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return null;
    }

    @DeleteMapping("aulaDelete/{id}")
    @Operation(summary = "Remover aula da agenda")
    public ResponseEntity<Void> cancelarAula(@PathVariable String id) {
        Optional<Aula> aula = aulaService.cancelarAula(id);
        if (aula.isPresent()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/listarAulas")
    @Operation(summary = "Agendar aula")
    public List<Aula> listarTodasAulas() {
        return aulaService.listarTodasAulas();
    }
}
