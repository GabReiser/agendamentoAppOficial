package com.agendamento.agendamentoApp.controller;

import com.agendamento.agendamentoApp.models.Aluno;
import com.agendamento.agendamentoApp.repositories.AlunoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/alunos")
public class AlunoController {

    @Autowired
    AlunoRepository alunoRepository;
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/getAllAlunos")
    @Operation(summary = "Get all Alunos")
    public List<Aluno> getAllAlunos(){
        return alunoRepository.findAll();
    }
    @GetMapping("/getAlunoById/{id}")
    @Operation(summary = "Get Aluno by Id")
    public ResponseEntity<Aluno>getAlunoById(@PathVariable String id){
        return alunoRepository.findById(id)
                .map(aluno -> ResponseEntity.ok().body(aluno))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/createAluno")
    @Operation(summary = "Create Aluno")
    public Aluno createAluno(@RequestBody String aluno) throws JsonProcessingException {
        Aluno alunoObject = objectMapper.readValue(aluno, Aluno.class);
        return alunoRepository.save(alunoObject);
    }
    @PutMapping("updateAluno/{id}")
    @Operation(summary = "Update aluno")
    public ResponseEntity<Aluno> updateAluno(@PathVariable String id, @RequestBody Aluno alunoDetails) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    aluno.setName(alunoDetails.getName());
                    aluno.setContact(alunoDetails.getContact());
                    Aluno updatedAluno = alunoRepository.save(aluno);
                    return ResponseEntity.ok().body(updatedAluno);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Aluno")
    public ResponseEntity<?> deleteAluno(@PathVariable String id) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    alunoRepository.delete(aluno);
                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
