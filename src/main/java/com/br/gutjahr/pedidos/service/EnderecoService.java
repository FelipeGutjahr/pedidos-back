package com.br.gutjahr.pedidos.service;

import com.br.gutjahr.pedidos.model.app.Endereco;
import com.br.gutjahr.pedidos.repository.EnderecoRepository;

import org.springframework.stereotype.Service;

@Service
public class EnderecoService extends CrudBaseService<Endereco, EnderecoRepository> {}
