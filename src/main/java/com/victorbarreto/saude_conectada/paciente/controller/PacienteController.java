package com.victorbarreto.saude_conectada.paciente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.victorbarreto.saude_conectada.paciente.dto.PacientePerfilDTO;
import com.victorbarreto.saude_conectada.paciente.service.PacienteService;

@RestController
@RequestMapping(value = "/api")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/perfil/paciente")
    public ResponseEntity<PacientePerfilDTO> criarPerfil(@RequestBody PacientePerfilDTO pacienteDTO,
                                                         Authentication authentication) { // Spring Security injeta o usuário logado aqui

        // O Controller não sabe "como" criar o perfil.
        // Ele apenas delega a responsabilidade para o Service.
        String emailUsuario = authentication.getName(); // Pega o email/username do token JWT

        PacientePerfilDTO perfilCriado = pacienteService.criarPerfil(pacienteDTO, emailUsuario);

        // Retorna o status 201 Created, que é o correto para criação de recursos.
        // O corpo da resposta contém os dados do perfil que foi criado.
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilCriado);
    }

    @PutMapping("/perfil/paciente")
    public ResponseEntity<PacientePerfilDTO> atualizarPerfil(@RequestBody PacientePerfilDTO pacientePerfilDTO,
                                                             Authentication authentication) {
        String emailUsuario = authentication.getName();
        pacientePerfilDTO = pacienteService.atualizarPerfil(pacientePerfilDTO, emailUsuario);

        return ResponseEntity.ok(pacientePerfilDTO);
    }

    @GetMapping("/perfil/paciente")
    public ResponseEntity<PacientePerfilDTO> buscarPerfil(Authentication authentication) {
        // Pega o email do usuário que vem no token JWT
        String emailUsuario = authentication.getName();

        // Pede ao service para buscar os dados do perfil com base no email
        PacientePerfilDTO perfil = pacienteService.buscarPerfil(emailUsuario);

        // Retorna os dados do perfil com status 200 OK
        return ResponseEntity.ok(perfil);
    }
}
