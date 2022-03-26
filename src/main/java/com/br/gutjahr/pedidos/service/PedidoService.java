package com.br.gutjahr.pedidos.service;

import com.br.gutjahr.pedidos.model.app.Pedido;
import com.br.gutjahr.pedidos.repository.PedidoRepository;

import org.springframework.stereotype.Service;

@Service
public class PedidoService extends CrudBaseService<Pedido, PedidoRepository> {}
