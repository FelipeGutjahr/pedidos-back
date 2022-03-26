package com.br.gutjahr.pedidos.service;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import com.br.gutjahr.pedidos.exception.Advertencia;
import com.br.gutjahr.pedidos.model.managment.Usuario;
import com.br.gutjahr.pedidos.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService extends CrudBaseService<Usuario, UsuarioRepository> {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder pe;
    @Autowired
    Flyway flyway;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario buscar(){
        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        return usuario.get();
    }

    public Usuario inserir(Usuario usuario){
        if (usuarioRepository.buscarPorEmail(usuario.getEmail()) != null) {
            throw new Advertencia("Email já cadastrado");
        }
        usuario.setId(null);
        usuario.setSenha(pe.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        usuario.setSchema("usuario_" + usuario.getId());
        usuarioRepository.save(usuario);
        criarSchema(usuario);
        return usuario;
    }

    public void criarSchema(Usuario usuario) {
        String migrationName = usuario.getIsRestaurante() ? "restaurante" : "cliente";
        DataSource dataSource = flyway.getConfiguration().getDataSource();
        Flyway cliente = Flyway.configure()
                .schemas(usuario.getSchema())
                .locations("db/migration/" + migrationName)
                .table("flyway_history")
                .baselineOnMigrate(true)
                .dataSource(dataSource).load();
        cliente.migrate();
    }

    /*public List<Usuario> listarRestaurantes() {
        return usuarioRepository.listarRestaurantes();
    }*/
}
