package com.victorbarreto.saude_conectada.profissional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.victorbarreto.saude_conectada.paciente.entity.PacienteMetaDiaria;
import com.victorbarreto.saude_conectada.paciente.entity.StatusMeta;
import com.victorbarreto.saude_conectada.paciente.pacienteRepository.PacienteMetaRepository;
import com.victorbarreto.saude_conectada.profissional.dto.ProfissionalMetaCreateDTO;
import com.victorbarreto.saude_conectada.profissional.dto.ProfissionalMetaResponseDTO;
import com.victorbarreto.saude_conectada.profissional.dto.ProfissionalPerfilDTO;
import com.victorbarreto.saude_conectada.profissional.entity.ProfissionalDadosModel;
import com.victorbarreto.saude_conectada.profissional.repository.ProfissionalRepository;
import com.victorbarreto.saude_conectada.usuario.entity.TipoUsuario;
import com.victorbarreto.saude_conectada.usuario.entity.UsuarioModel;
import com.victorbarreto.saude_conectada.usuario.repository.UsuarioRepository;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PacienteMetaRepository pacienteMetaRepository;

    //POST
    public ProfissionalPerfilDTO criarPerfil(ProfissionalPerfilDTO profissionalPerfilDTO, String emailUsuarioLogado) {
        UsuarioModel usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com email " + emailUsuarioLogado + " não encontrado."));

        if (profissionalRepository.existsByUsuarioModel(usuario)) {
            throw new IllegalStateException("Este usuário já possui um perfil de profissional cadastrado.");
        }

        ProfissionalDadosModel profissionalDadosModel = new ProfissionalDadosModel();

        profissionalDadosModel.setUsuarioModel(usuario);
        profissionalDadosModel.setCrm(profissionalPerfilDTO.ufCrm());
        profissionalDadosModel.setEspecialidade(profissionalPerfilDTO.especialidade());
        profissionalDadosModel.setUfCrm(profissionalPerfilDTO.ufCrm());

        profissionalRepository.save(profissionalDadosModel);

        return new ProfissionalPerfilDTO(
                profissionalDadosModel.getCrm(),
                profissionalDadosModel.getUfCrm(),
                profissionalDadosModel.getEspecialidade()
        );
    }

    //PUT
    public ProfissionalPerfilDTO atualizarPerfil(ProfissionalPerfilDTO profissionalPerfilDTO,
                                                 String emailUsuarioLogado) {

        UsuarioModel usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com email " + emailUsuarioLogado + " não encontrado."));


        ProfissionalDadosModel profissionalDadosModel = profissionalRepository.findByUsuarioModel(usuario)
                .orElse(new ProfissionalDadosModel());


        profissionalDadosModel.setUsuarioModel(usuario);


        profissionalDadosModel.setCrm(profissionalPerfilDTO.crm());
        profissionalDadosModel.setEspecialidade(profissionalPerfilDTO.especialidade());
        profissionalDadosModel.setUfCrm(profissionalPerfilDTO.ufCrm());


        ProfissionalDadosModel perfilSalvo = profissionalRepository.save(profissionalDadosModel);


        return new ProfissionalPerfilDTO(perfilSalvo.getCrm(), perfilSalvo.getUfCrm(), perfilSalvo.getEspecialidade());
    }

    public ProfissionalPerfilDTO buscarPerfil(String emailUsuario) {
        UsuarioModel usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + emailUsuario));

        var perfilOpt = profissionalRepository.findByUsuarioModelId(usuario.getId());

        if (perfilOpt.isPresent()) {
            var perfil = perfilOpt.get();
            return new ProfissionalPerfilDTO(perfil.getCrm(), perfil.getUfCrm(), perfil.getEspecialidade());
        } else {
            return new ProfissionalPerfilDTO(null, null, null);
        }
    }

    public ProfissionalMetaResponseDTO criarMetaParaPaciente(Long pacienteId, ProfissionalMetaCreateDTO metaDTO) {
        UsuarioModel paciente = usuarioRepository.findById(pacienteId)
                .orElseThrow(() -> new UsernameNotFoundException("Paciente com ID " + pacienteId + " não encontrado."));


        if (paciente.getTipo() != TipoUsuario.PACIENTE) {
            throw new IllegalStateException("O usuário com ID " + pacienteId + " não é um paciente.");
        }

        PacienteMetaDiaria novaMeta = new PacienteMetaDiaria();

        novaMeta.setDescricao(metaDTO.descricao());
        novaMeta.setDataMeta(metaDTO.dataMeta());
        novaMeta.setStatus(StatusMeta.PENDENTE);
        novaMeta.setPaciente(paciente);

        PacienteMetaDiaria metaSalva = pacienteMetaRepository.save(novaMeta);

        return new ProfissionalMetaResponseDTO(
                metaSalva.getId(),
                metaSalva.getDescricao(),
                metaSalva.getDataMeta(),
                metaSalva.getStatus()
        );
    }
}
