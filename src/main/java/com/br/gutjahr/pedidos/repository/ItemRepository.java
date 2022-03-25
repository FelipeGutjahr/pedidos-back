package com.br.gutjahr.pedidos.repository;

import com.br.gutjahr.pedidos.model.app.Item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {}
