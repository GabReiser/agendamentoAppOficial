package com.agendamentohub.agendamento.controller;

import com.agendamentohub.agendamento.dto.AgendamentoResponseDTO;
import com.agendamentohub.agendamento.dto.AlterarStatusDTO;
import com.agendamentohub.agendamento.dto.AtualizarAgendamentoDTO;
import com.agendamentohub.agendamento.dto.CriarAgendamentoDTO;
import com.agendamentohub.agendamento.model.Agendamentos;
import com.agendamentohub.agendamento.model.Role;
import com.agendamentohub.agendamento.model.StatusAgendamento;
import com.agendamentohub.agendamento.model.User;
import com.agendamentohub.agendamento.service.AgendamentoService;
import com.agendamentohub.agendamento.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;
    private final UserService userService;

    public AgendamentoController(AgendamentoService agendamentoService, UserService userService) {
        this.agendamentoService = agendamentoService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> criarAgendamento(@RequestBody CriarAgendamentoDTO agendamento,
                                              @AuthenticationPrincipal String email) {
        agendamentoService.criarAgendamento(email,agendamento);
        return ResponseEntity.ok("Agendamento criado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAgendamento(@PathVariable String id,
                                                  @RequestBody AtualizarAgendamentoDTO agendamentoDTO,
                                                  @AuthenticationPrincipal String email) {
        // Busca o usuário do banco usando o email
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se o usuário é ADMIN
        boolean isAdmin = user.getRoles().contains(Role.ADMIN);

        // Passa para o service se é admin ou não
        agendamentoService.atualizarAgendamento(id, agendamentoDTO, isAdmin);

        return ResponseEntity.ok("Agendamento atualizado com sucesso!");
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<AgendamentoResponseDTO>> listarAgendamentos(@AuthenticationPrincipal String email) {
        List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarAgendamentos(email);
        return ResponseEntity.ok(agendamentos);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> cancelarAgendamento(@PathVariable String id) {
        agendamentoService.alterarStatus(id, StatusAgendamento.CANCELED);
        return ResponseEntity.ok("Agendamento cancelado com sucesso!");
    }

}
