package com.victorbarreto.saude_conectada.profissional.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.victorbarreto.saude_conectada.profissional.entity.ProfissionalDadosModel;
import com.victorbarreto.saude_conectada.usuario.entity.UsuarioModel;

public interface ProfissionalRepository extends JpaRepository<ProfissionalDadosModel, Long> {
    boolean existsByUsuarioModel(UsuarioModel usuario);

    Optional<ProfissionalDadosModel> findByUsuarioModel(UsuarioModel usuario);

    Optional<ProfissionalDadosModel> findByUsuarioModelId(Long id);
}
