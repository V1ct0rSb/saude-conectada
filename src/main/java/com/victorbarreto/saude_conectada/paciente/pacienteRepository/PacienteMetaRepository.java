package com.victorbarreto.saude_conectada.paciente.pacienteRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.victorbarreto.saude_conectada.paciente.entity.PacienteMetaDiaria;
import com.victorbarreto.saude_conectada.usuario.entity.UsuarioModel;

public interface PacienteMetaRepository extends JpaRepository<PacienteMetaDiaria, Long> {
    List<PacienteMetaDiaria> findByPaciente(UsuarioModel paciente);
}
