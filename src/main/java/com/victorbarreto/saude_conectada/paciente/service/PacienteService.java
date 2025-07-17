package com.victorbarreto.saude_conectada.paciente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.victorbarreto.saude_conectada.paciente.dto.PacientePerfilDTO;
import com.victorbarreto.saude_conectada.paciente.entity.PacienteDadosModel;
import com.victorbarreto.saude_conectada.paciente.pacienteRepository.PacienteRepository;
import com.victorbarreto.saude_conectada.usuario.entity.UsuarioModel;
import com.victorbarreto.saude_conectada.usuario.repository.UsuarioRepository;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //POST
    public PacientePerfilDTO criarPerfil(PacientePerfilDTO pacientePerfilDTO, String emailUsuarioLogado) {
        // 1. Buscar o usuário que está fazendo a requisição
        UsuarioModel usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com email " + emailUsuarioLogado + " não encontrado."));

        // 2. Regra de Negócio: Verificar se o perfil já não foi criado
        if (pacienteRepository.existsByUsuarioModel(usuario)) {
            throw new IllegalStateException("Este usuário já possui um perfil de paciente cadastrado.");
        }

        // 3. Criar a nova entidade de dados do paciente
        PacienteDadosModel pacienteDadosModel = new PacienteDadosModel();

        // 4. Mapear os dados do DTO para a Entidade e associar o usuário
        pacienteDadosModel.setUsuarioModel(usuario); // Ligando as "gavetas"
        pacienteDadosModel.setPeso(pacientePerfilDTO.peso());
        pacienteDadosModel.setAltura(pacientePerfilDTO.altura());
        pacienteDadosModel.setDataNascimento(pacientePerfilDTO.dataNascimento());
        pacienteDadosModel.setComorbidades(pacientePerfilDTO.comorbidades());

        // 5. Salvar a nova entidade no banco de dados
        pacienteRepository.save(pacienteDadosModel);

        // 6. Mapear a entidade salva de volta para um DTO para retornar ao Controller
        return new PacientePerfilDTO(
                pacienteDadosModel.getPeso(),
                pacienteDadosModel.getDataNascimento(),
                pacienteDadosModel.getComorbidades(),
                pacienteDadosModel.getAltura()
        );
    }

    //PUT
    public PacientePerfilDTO atualizarPerfil(PacientePerfilDTO pacientePerfilDTO, String emailUsuarioLogado) {
        // 1. Buscar o usuário que está fazendo a requisição
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


}
