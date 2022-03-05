package com.br.gutjahr.pedidos.service;

import com.br.gutjahr.pedidos.model.managment.Usuario;
import com.br.gutjahr.pedidos.repository.UsuarioRepository;
import com.br.gutjahr.pedidos.security.UserSS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException(email);
        }
        return new UserSS(usuario.getId(), usuario.getEmail(), usuario.getSenha(),
                usuario.getPerfil());
    }
}
