package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.repositories.UsuarioRepository;
import com.br.gutjahr.despesas.services.exceptions.DataIntegrityExeption;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder pe;
    @Autowired
    Flyway flyway;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario find(){
        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        return usuario.get();
    }

    public Usuario insert(Usuario usuario){
        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            throw new DataIntegrityExeption("E-mail já cadastrado");
        }
        usuario.setId(null);
        usuario.setSenha(pe.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        usuario.setSchema("cliente_" + usuario.getId());
        usuarioRepository.save(usuario);
        criarSchema(usuario.getSchema());
        return usuario;
    }

    public void criarSchema(String schema) {
        DataSource dataSource = flyway.getConfiguration().getDataSource();
        Flyway cliente = Flyway.configure()
                .schemas(schema)
                .locations("db/migration/cliente")
                .table("flyway_history")
                .baselineOnMigrate(true)
                .dataSource(dataSource).load();
        cliente.migrate();
    }
}
