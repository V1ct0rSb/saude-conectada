package com.victorbarreto.saude_conectada.usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.victorbarreto.saude_conectada.usuario.dto.UsuarioCreateDTO;
import com.victorbarreto.saude_conectada.usuario.entity.UsuarioModel;
import com.victorbarreto.saude_conectada.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //POST
    public UsuarioModel cadastrar(UsuarioCreateDTO usuarioCreateDTO) {
        if (usuarioRepository.existsByEmail(usuarioCreateDTO.email())) {
            throw new RuntimeException("Email já cadastrado no sistema");
        }
        UsuarioModel usuarioModel = new UsuarioModel();
        String senhaCriptografada = passwordEncoder.encode(usuarioCreateDTO.senha());

        usuarioModel.setEmail(usuarioCreateDTO.email());
        usuarioModel.setNome(usuarioCreateDTO.nome());
        usuarioModel.setTipo(usuarioCreateDTO.tipoUsuario());
        usuarioModel.setSenha(senhaCriptografada);

        return usuarioRepository.save(usuarioModel);
    }

    //GET
    public List<UsuarioModel> listarUsuarios(){
        return usuarioRepository.findAll();
    }

}
