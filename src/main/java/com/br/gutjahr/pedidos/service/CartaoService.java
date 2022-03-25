package com.br.gutjahr.pedidos.service;

import com.br.gutjahr.pedidos.model.app.Cartao;
import com.br.gutjahr.pedidos.repository.CartaoRepository;

import org.springframework.stereotype.Service;

@Service
public class CartaoService extends CrudBaseService<Cartao, CartaoRepository> {}
