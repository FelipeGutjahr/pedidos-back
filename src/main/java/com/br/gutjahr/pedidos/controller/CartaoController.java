package com.br.gutjahr.pedidos.controller;

import com.br.gutjahr.pedidos.model.app.Cartao;
import com.br.gutjahr.pedidos.repository.CartaoRepository;
import com.br.gutjahr.pedidos.service.CartaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cartao")
public class CartaoController extends CrudBaseController<Cartao, CartaoRepository, CartaoService> {

    @Autowired
    protected CartaoController(CartaoService cartaoService) {
        super(cartaoService);
    }
}
