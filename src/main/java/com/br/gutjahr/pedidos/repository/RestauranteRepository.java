package com.br.gutjahr.pedidos.repository;
import java.util.List;

import com.br.gutjahr.pedidos.model.managment.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestauranteRepository extends JpaRepository<Usuario, Integer> {
    
    String FIND_ALL = "SELECT new Usuario(u.id, u.nome, u.imgUrl) FROM Usuario u WHERE u.isRestaurante = true";
    String GET_ONE = "SELECT new Usuario(u.id, u.nome, u.email, u.telefone, u.dataCadastro, u.imgUrl, u.schema) FROM Usuario u WHERE u.id = :id";

    @Query(FIND_ALL)
    List<Usuario> findAll();
    @Query(GET_ONE)
    Usuario getOne(@Param("id") Integer id);
}
