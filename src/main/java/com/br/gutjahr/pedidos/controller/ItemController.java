package com.br.gutjahr.pedidos.controller;

import com.br.gutjahr.pedidos.model.app.Item;
import com.br.gutjahr.pedidos.repository.ItemRepository;
import com.br.gutjahr.pedidos.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/item")
public class ItemController extends CrudBaseController<Item, ItemRepository, ItemService> {
    
    @Autowired
    protected ItemController(ItemService itemService) {
        super(itemService);
    }
}
