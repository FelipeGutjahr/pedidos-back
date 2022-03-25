package com.br.gutjahr.pedidos.service;

import java.util.List;

import com.br.gutjahr.pedidos.config.TenantContext;
import com.br.gutjahr.pedidos.model.app.Item;
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
    @Autowired
    private DatabaseSessionManager databaseSessionManager;
    @Autowired
    private TenantContext tenantContext;

    @Override
    public Usuario getOne(Integer id) {
        Usuario usuario = restauranteRepository.getOne(id);
        if(usuario != null) {
            usuario.setItens(carregarItens(usuario.getSchema()));
        }
        return usuario;
    }

    private List<Item> carregarItens(String schema) {
        databaseSessionManager.unbindSession();
        tenantContext.setSchema(schema);
        databaseSessionManager.bindSession();
        return itemService.findAll();
    }
}
