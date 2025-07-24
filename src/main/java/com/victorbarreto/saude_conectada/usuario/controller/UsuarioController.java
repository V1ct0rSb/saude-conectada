package com.victorbarreto.saude_conectada.usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.victorbarreto.saude_conectada.security.JwtUtil;
import com.victorbarreto.saude_conectada.usuario.dto.TokenDTO;
import com.victorbarreto.saude_conectada.usuario.dto.UsuarioCreateDTO;
import com.victorbarreto.saude_conectada.usuario.dto.UsuarioLoginDTO;
import com.victorbarreto.saude_conectada.usuario.dto.UsuarioLoginResponseDTO;
import com.victorbarreto.saude_conectada.usuario.entity.UsuarioModel;
import com.victorbarreto.saude_conectada.usuario.service.UsuarioService;

@RestController
@RequestMapping(value = "/api")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioModel>> listarUsuarios() {
        List<UsuarioModel> usuarioModelList = usuarioService.listarUsuarios();
        return ResponseEntity.ok().body(usuarioModelList);
    }

    @PostMapping("/usuario")
    public ResponseEntity<UsuarioModel> cadastrar(@RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        UsuarioModel usuarioModel = usuarioService.cadastrar(usuarioCreateDTO);
        return ResponseEntity.status(201).body(usuarioModel);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioLoginResponseDTO> login(@RequestBody UsuarioLoginDTO usuarioLoginDTO) { // <-- Altere o tipo de retorno
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioLoginDTO.email(),
                usuarioLoginDTO.senha()
        ));

        // Pega o UsuarioModel completo, que tem todas as informações
        UsuarioModel usuarioAutenticado = (UsuarioModel) authentication.getPrincipal();
        String jwtToken = jwtUtil.generateToken((UserDetails) usuarioAutenticado);

        // Cria o novo DTO de resposta com todas as informações
        UsuarioLoginResponseDTO response = new UsuarioLoginResponseDTO(
                jwtToken,
                usuarioAutenticado.getNome(),
                usuarioAutenticado.getEmail(),
                usuarioAutenticado.getTipo()
        );

        return ResponseEntity.ok(response);
    }

}
