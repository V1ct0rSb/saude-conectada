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
                                                         Authentication authentication) {

        String emailUsuario = authentication.getName();

        PacientePerfilDTO perfilCriado = pacienteService.criarPerfil(pacienteDTO, emailUsuario);

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

        String emailUsuario = authentication.getName();


        PacientePerfilDTO perfil = pacienteService.buscarPerfil(emailUsuario);


        return ResponseEntity.ok(perfil);
    }
}
