package com.victorbarreto.saude_conectada.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.victorbarreto.saude_conectada.usuario.entity.UsuarioModel;
import com.victorbarreto.saude_conectada.usuario.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Carrega os detalhes de um usuário pelo seu email (que usamos como username).
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioModel usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        // Cria uma lista de permissões (roles) a partir do tipo de usuário
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(usuario.getTipo().name()));

        // Retorna um objeto User do Spring Security
        return new User(usuario.getEmail(), usuario.getSenha(), authorities);
    }
}