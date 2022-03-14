package com.br.gutjahr.pedidos.controller;

import com.br.gutjahr.pedidos.model.managment.Usuario;
import com.br.gutjahr.pedidos.repository.RestauranteRepository;
import com.br.gutjahr.pedidos.service.RestauranteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/restaurante")
public class RestauranteController extends CrudBaseController<Usuario, RestauranteRepository, RestauranteService> {
    
    @Autowired
    protected RestauranteController(RestauranteService restauranteService) {
        super(restauranteService);
    }
}
