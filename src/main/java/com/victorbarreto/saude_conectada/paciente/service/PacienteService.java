package com.victorbarreto.saude_conectada.paciente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.victorbarreto.saude_conectada.paciente.dto.PacientePerfilDTO;
import com.victorbarreto.saude_conectada.paciente.entity.PacienteDadosModel;
import com.victorbarreto.saude_conectada.paciente.entity.PacienteMetaDiaria;
import com.victorbarreto.saude_conectada.paciente.pacienteRepository.PacienteMetaRepository;
import com.victorbarreto.saude_conectada.paciente.pacienteRepository.PacienteRepository;
import com.victorbarreto.saude_conectada.usuario.entity.UsuarioModel;
import com.victorbarreto.saude_conectada.usuario.repository.UsuarioRepository;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PacienteMetaRepository pacienteMetaRepository;

    //POST
    public PacientePerfilDTO criarPerfil(PacientePerfilDTO pacientePerfilDTO, String emailUsuarioLogado) {

        UsuarioModel usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com email " + emailUsuarioLogado + " não encontrado."));

        if (pacienteRepository.existsByUsuarioModel(usuario)) {
            throw new IllegalStateException("Este usuário já possui um perfil de paciente cadastrado.");
        }
        PacienteDadosModel pacienteDadosModel = new PacienteDadosModel();

        pacienteDadosModel.setUsuarioModel(usuario);
        pacienteDadosModel.setPeso(pacientePerfilDTO.peso());
        pacienteDadosModel.setAltura(pacientePerfilDTO.altura());
        pacienteDadosModel.setDataNascimento(pacientePerfilDTO.dataNascimento());
        pacienteDadosModel.setComorbidades(pacientePerfilDTO.comorbidades());

        pacienteRepository.save(pacienteDadosModel);

        return new PacientePerfilDTO(
                pacienteDadosModel.getPeso(),
                pacienteDadosModel.getDataNascimento(),
                pacienteDadosModel.getComorbidades(),
                pacienteDadosModel.getAltura()
        );
    }

    //PUT
    public PacientePerfilDTO atualizarPerfil(PacientePerfilDTO pacientePerfilDTO, String emailUsuarioLogado) {

        UsuarioModel usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com email " + emailUsuarioLogado + " não encontrado."));

        PacienteDadosModel pacienteDadosModel = pacienteRepository.findByUsuarioModel(usuario)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado!"));

        pacienteDadosModel.setPeso(pacientePerfilDTO.peso());
        pacienteDadosModel.setAltura(pacientePerfilDTO.altura());
        pacienteDadosModel.setComorbidades(pacientePerfilDTO.comorbidades());
        pacienteDadosModel.setDataNascimento(pacientePerfilDTO.dataNascimento());

        pacienteRepository.save(pacienteDadosModel);

        return new PacientePerfilDTO(
                pacienteDadosModel.getPeso(),
                pacienteDadosModel.getDataNascimento(),
                pacienteDadosModel.getComorbidades(),
                pacienteDadosModel.getAltura()
        );
    }

    //GET
    public PacientePerfilDTO buscarPerfil(String emailUsuarioLogado) {

        UsuarioModel usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + emailUsuarioLogado));

        var perfilOpt = pacienteRepository.findByUsuarioModelId(usuario.getId());

        if (perfilOpt.isPresent()) {
            var perfil = perfilOpt.get();
            return new PacientePerfilDTO(
                    perfil.getAltura(),
                    perfil.getDataNascimento(),
                    perfil.getComorbidades(),
                    perfil.getPeso()
            );
        } else {
            return new PacientePerfilDTO(null, null, null, null);
        }
    }

    //GET
    public List<PacienteMetaDiaria> exibirMeta(String emailUsuarioLogado) {
        UsuarioModel usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + emailUsuarioLogado));

        if (!pacienteRepository.existsByUsuarioModel(usuario)) {
            throw new RuntimeException("Perfil de paciente não encontrado para este usuário.");
        }

        return pacienteMetaRepository.findByPaciente(usuario);
    }


}
