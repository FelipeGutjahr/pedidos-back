package com.br.gutjahr.pedidos.repository;

import java.util.List;

import com.br.gutjahr.pedidos.model.app.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    String FIND_ALL = "SELECT new Item(i.id, i.nome, i.preco, i.imgUrl) FROM Item i";
    String GET_ONE = "SELECT new Item(i.id, i.nome, i.preco, i.descricao, i.imgUrl) FROM Item i WHERE i.id = :id";

    @Query(FIND_ALL)
    List<Item> findAll();
    @Query(GET_ONE)
    Item getOne(@Param("id") Integer id);
}
