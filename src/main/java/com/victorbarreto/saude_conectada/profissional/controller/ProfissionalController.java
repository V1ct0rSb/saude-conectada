package com.victorbarreto.saude_conectada.profissional.controller;

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

import com.victorbarreto.saude_conectada.profissional.dto.ProfissionalPerfilDTO;
import com.victorbarreto.saude_conectada.profissional.service.ProfissionalService;

@RestController
@RequestMapping("/api")
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;


    @GetMapping("/perfil/profissional")
    public ResponseEntity<ProfissionalPerfilDTO> buscarPerfil(Authentication authentication) {
        String emailUsuario = authentication.getName(); // Pega o email do token JWT
        ProfissionalPerfilDTO perfil = profissionalService.buscarPerfil(emailUsuario);
        return ResponseEntity.ok(perfil);
    }


    @PostMapping("/perfil/profissional")
    public ResponseEntity<ProfissionalPerfilDTO> criarPerfil(@RequestBody ProfissionalPerfilDTO profissionalDTO,
                                                             Authentication authentication) {
        String emailUsuario = authentication.getName();
        ProfissionalPerfilDTO perfilCriado = profissionalService.criarPerfil(profissionalDTO, emailUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilCriado);
    }

    @PutMapping("/perfil/profissional")
    public ResponseEntity<ProfissionalPerfilDTO> atualizarPerfil(@RequestBody ProfissionalPerfilDTO profissionalDTO,
                                                                 Authentication authentication) {
        String emailUsuario = authentication.getName();
        ProfissionalPerfilDTO perfilAtualizado = profissionalService.atualizarPerfil(profissionalDTO, emailUsuario);
        return ResponseEntity.ok(perfilAtualizado);
    }
}