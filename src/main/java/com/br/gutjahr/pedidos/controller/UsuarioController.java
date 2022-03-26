package com.br.gutjahr.pedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import com.br.gutjahr.pedidos.model.managment.Usuario;
import com.br.gutjahr.pedidos.service.UsuarioService;

import java.net.URI;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping()
    public ResponseEntity<?> buscar(){
        Usuario usuario = usuarioService.buscar();
        return ResponseEntity.ok().body(usuario);
    }

    @PostMapping()
    public ResponseEntity<Void> inserir(@Valid @RequestBody Usuario usuario){
        usuario = usuarioService.inserir(usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /*@GetMapping(value = "/listar_restaurantes")
    public ResponseEntity<?> listarRestaurantes() {
        List<Usuario> restaurantes = usuarioService.listarRestaurantes();
        return ResponseEntity.ok().body(restaurantes);
    }*/
}
