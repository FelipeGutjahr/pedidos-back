package com.br.gutjahr.pedidos.service;

import com.br.gutjahr.pedidos.model.app.Item;
import com.br.gutjahr.pedidos.repository.ItemRepository;

import org.springframework.stereotype.Service;

@Service
public class ItemService extends CrudBaseService<Item, ItemRepository> {

    public Item visualizarItem(Integer itemId, Integer restauranteId) {
        alterarSchemaPorUsuarioId(restauranteId);
        return getModelRepository().getOne(itemId);
    }
}
