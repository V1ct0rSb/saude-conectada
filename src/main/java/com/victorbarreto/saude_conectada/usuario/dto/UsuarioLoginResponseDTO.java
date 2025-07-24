package com.victorbarreto.saude_conectada.usuario.dto;

import com.victorbarreto.saude_conectada.usuario.entity.TipoUsuario;

public record UsuarioLoginResponseDTO(String token, String nome, String email, TipoUsuario tipoUsuario) {
}