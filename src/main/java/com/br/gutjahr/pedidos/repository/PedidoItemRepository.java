package com.br.gutjahr.pedidos.repository;

import com.br.gutjahr.pedidos.model.app.PedidoItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Integer> {}
