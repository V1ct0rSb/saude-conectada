package com.victorbarreto.saude_conectada.usuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.victorbarreto.saude_conectada.usuario.entity.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {


    boolean existsByEmail(String email);

    Optional<UsuarioModel> findByEmail(String email);
}
