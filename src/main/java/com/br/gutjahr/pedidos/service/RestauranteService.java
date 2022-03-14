package com.br.gutjahr.pedidos.service;

import com.br.gutjahr.pedidos.model.managment.Usuario;
import com.br.gutjahr.pedidos.repository.RestauranteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestauranteService extends CrudBaseService<Usuario, RestauranteRepository> {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Override
    public Usuario getOne(Integer id) {
        Usuario usuario = restauranteRepository.getOne(id);
        if(usuario != null) {
            // TODO: Settar a lista de itens no usuário
        }
        return usuario;
    }
}
