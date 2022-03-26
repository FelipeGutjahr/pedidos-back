package com.br.gutjahr.pedidos.repository;

import com.br.gutjahr.pedidos.model.app.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {}
