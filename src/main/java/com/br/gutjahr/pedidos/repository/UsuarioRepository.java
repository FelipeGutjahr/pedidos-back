package com.br.gutjahr.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import com.br.gutjahr.pedidos.model.managment.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Transactional(readOnly = true)
    Usuario findByEmail(String email);

    @Transactional(readOnly = true)
    Optional<Usuario> findById(Integer id);
}
