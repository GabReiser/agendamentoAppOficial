package com.agendamento.agendamentoApp.controller;

import com.agendamento.agendamentoApp.models.Aluno;
import com.agendamento.agendamentoApp.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/alunos")
public class AlunoController {

    @Autowired
    AlunoRepository alunoRepository;

    @GetMapping
    public List<Aluno> getAllAlunos(){
        return alunoRepository.findAll();
    }
    @GetMapping
    public ResponseEntity<Aluno>getAlunoById(@PathVariable String id){
        return alunoRepository.findById(id)
                .map(aluno -> ResponseEntity.ok().body(aluno))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public Aluno createAluno(@RequestBody Aluno aluno){
        return alunoRepository.save(aluno);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> updateAluno(@PathVariable String id, @RequestBody Aluno alunoDetails) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    aluno.setName(alunoDetails.getName());
                    aluno.setContact(alunoDetails.getContact());
                    Aluno updatedAluno = alunoRepository.save(aluno);
                    return ResponseEntity.ok().body(updatedAluno);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
