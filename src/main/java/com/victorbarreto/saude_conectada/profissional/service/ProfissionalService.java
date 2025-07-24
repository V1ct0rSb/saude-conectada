package com.victorbarreto.saude_conectada.profissional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.victorbarreto.saude_conectada.paciente.dto.PacientePerfilDTO;
import com.victorbarreto.saude_conectada.profissional.dto.ProfissionalPerfilDTO;
import com.victorbarreto.saude_conectada.profissional.entity.ProfissionalDadosModel;
import com.victorbarreto.saude_conectada.profissional.repository.ProfissionalRepository;
import com.victorbarreto.saude_conectada.usuario.entity.UsuarioModel;
import com.victorbarreto.saude_conectada.usuario.repository.UsuarioRepository;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        // 1. Busca o usuário principal
        UsuarioModel usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com email " + emailUsuarioLogado + " não encontrado."));

        // 2. Busca o perfil. Se não existir, cria um novo objeto.
        ProfissionalDadosModel profissionalDadosModel = profissionalRepository.findByUsuarioModel(usuario)
                .orElse(new ProfissionalDadosModel());

        // 3. Associa o usuário ao perfil (importante para o caso de ser um novo)
        profissionalDadosModel.setUsuarioModel(usuario);

        // 4. Atualiza os dados do perfil com o que veio do formulário
        // CORRIGI UM BUG AQUI: estava usando ufCrm no lugar de crm
        profissionalDadosModel.setCrm(profissionalPerfilDTO.crm());
        profissionalDadosModel.setEspecialidade(profissionalPerfilDTO.especialidade());
        profissionalDadosModel.setUfCrm(profissionalPerfilDTO.ufCrm());

        // 5. Salva (cria ou atualiza) o perfil no banco
        ProfissionalDadosModel perfilSalvo = profissionalRepository.save(profissionalDadosModel);

        // 6. Retorna o DTO com os dados salvos
        return new ProfissionalPerfilDTO(
                perfilSalvo.getCrm(),
                perfilSalvo.getUfCrm(),
                perfilSalvo.getEspecialidade()
        );
    }

    public ProfissionalPerfilDTO buscarPerfil(String emailUsuario) {
        UsuarioModel usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + emailUsuario));

        var perfilOpt = profissionalRepository.findByUsuarioModelId(usuario.getId());

        if (perfilOpt.isPresent()) {
            var perfil = perfilOpt.get();
            return new ProfissionalPerfilDTO(
                    perfil.getCrm(),
                    perfil.getUfCrm(),
                    perfil.getEspecialidade()
            );
        } else {
            return new ProfissionalPerfilDTO(null, null, null);
        }
    }
}
