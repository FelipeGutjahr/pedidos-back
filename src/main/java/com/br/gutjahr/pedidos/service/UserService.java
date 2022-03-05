package com.br.gutjahr.pedidos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.br.gutjahr.pedidos.model.managment.Usuario;
import com.br.gutjahr.pedidos.repository.UsuarioRepository;
import com.br.gutjahr.pedidos.security.UserSS;

@Service
public class UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> authencated(){
        try {
            UserSS userSS = (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(userSS == null) throw new ArrayStoreException("Acesso negado");

            Optional<Usuario> usuario = usuarioRepository.findById(userSS.getId());
            return Optional.ofNullable(usuario.get());
        } catch (Exception e){
            throw new ArrayStoreException("Ocorreu um erro ao obter o usuário autenticado");
        }
    }
}
