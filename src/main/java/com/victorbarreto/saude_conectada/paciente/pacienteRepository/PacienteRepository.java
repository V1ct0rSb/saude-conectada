package com.victorbarreto.saude_conectada.paciente.pacienteRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.victorbarreto.saude_conectada.paciente.entity.PacienteDadosModel;
import com.victorbarreto.saude_conectada.usuario.entity.UsuarioModel;

public interface PacienteRepository extends JpaRepository<PacienteDadosModel, Long> {
    boolean existsByUsuarioModel(UsuarioModel usuario);

    Optional<PacienteDadosModel> findByUsuarioModel(UsuarioModel usuario);
}
