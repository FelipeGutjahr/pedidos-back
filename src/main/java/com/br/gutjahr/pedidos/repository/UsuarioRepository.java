package com.br.gutjahr.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.br.gutjahr.pedidos.model.managment.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    String LISTAR_RESTAURANTES = "SELECT new Usuario(u.id, u.nome, u.avaliacao) FROM Usuario u WHERE u.isRestaurante = true";
    String FIND_BY_EMAIL = "SELECT new Usuario(u.id, u.nome, u.avaliacao) FROM Usuario u WHERE u.email = :email";

    @Query(FIND_BY_EMAIL)
    Usuario findByEmail(@Param("email") String email);

    @Query(LISTAR_RESTAURANTES)
    List<Usuario> listarRestaurantes();
}
