package com.br.gutjahr.pedidos.controller;

import com.br.gutjahr.pedidos.model.app.Pedido;
import com.br.gutjahr.pedidos.repository.PedidoRepository;
import com.br.gutjahr.pedidos.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pedido")
public class PedidoController extends CrudBaseController<Pedido, PedidoRepository, PedidoService> {
    
    @Autowired
    protected PedidoController(PedidoService pedidoService) {
        super(pedidoService);
    }
}
