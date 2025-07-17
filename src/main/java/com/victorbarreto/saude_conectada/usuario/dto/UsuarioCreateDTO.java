package com.victorbarreto.saude_conectada.usuario.dto;

import com.victorbarreto.saude_conectada.usuario.entity.TipoUsuario;

public record UsuarioCreateDTO(String nome, String email, String senha, TipoUsuario tipoUsuario) {
}
