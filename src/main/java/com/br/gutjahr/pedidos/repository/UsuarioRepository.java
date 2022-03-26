package com.br.gutjahr.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import com.br.gutjahr.pedidos.model.managment.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    String FIND_BY_EMAIL = "SELECT new Usuario(u.id, u.nome, u.email, u.senha, u.schema) FROM Usuario u WHERE u.email = :email";

    @Query(FIND_BY_EMAIL)
    Usuario buscarPorEmail(@Param("email") String email);
    @Transactional(readOnly = true)
    Optional<Usuario> findById(Integer id);
}
