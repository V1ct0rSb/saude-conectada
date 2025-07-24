package com.victorbarreto.saude_conectada.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
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
        // CORREÇÃO:
        // Agora você simplesmente busca e retorna o seu UsuarioModel.
        // O Spring Security vai usar os métodos que você implementou no Passo 1.
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
    }
}