package com.br.gutjahr.pedidos.service;

import com.br.gutjahr.pedidos.model.managment.Usuario;
import com.br.gutjahr.pedidos.repository.RestauranteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestauranteService extends CrudBaseService<Usuario, RestauranteRepository> {

    @Autowired
    private ItemService itemService;
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Override
    public Usuario getOne(Integer id) {
        Usuario usuario = restauranteRepository.getOne(id);
        alterarSchemaPorUsuarioId(id);
        if(usuario != null) {
            usuario.setItens(itemService.findAll());
        }
        return usuario;
    }
}
