package com.br.gutjahr.pedidos.controller;

import com.br.gutjahr.pedidos.model.app.Endereco;
import com.br.gutjahr.pedidos.repository.EnderecoRepository;
import com.br.gutjahr.pedidos.service.EnderecoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/endereco")
public class EnderecoController extends CrudBaseController<Endereco, EnderecoRepository, EnderecoService> {
    
    @Autowired
    protected EnderecoController(EnderecoService enderecoService) {
        super(enderecoService);
    }
}
