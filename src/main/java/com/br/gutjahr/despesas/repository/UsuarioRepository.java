package com.br.gutjahr.despesas.repository;

import com.br.gutjahr.despesas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Transactional(readOnly = true)
    Usuario findByEmail(String email);

    @Transactional(readOnly = true)
    Optional<Usuario> findById(Integer id);
}
