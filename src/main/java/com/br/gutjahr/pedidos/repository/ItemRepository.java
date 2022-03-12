package com.br.gutjahr.pedidos.repository;

import com.br.gutjahr.pedidos.model.app.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    
    String GET_ONE = "SELECT new Item(i.id, i.nome, i.preco, i.descricao) FROM Item i WHERE i.id = :id";

    @Query(GET_ONE)
    Item getOne(@Param("id") Integer id);
}
