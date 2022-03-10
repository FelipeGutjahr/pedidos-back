package com.br.gutjahr.pedidos.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.br.gutjahr.pedidos.model.app.CrudBaseModel;
import com.br.gutjahr.pedidos.service.CrudBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class CrudBaseController<M extends CrudBaseModel<Integer>,
        R extends JpaRepository<M, Integer>, S extends CrudBaseService<M, R>> {
    
    private S modelService;

    protected CrudBaseController(S modelService) {
        this.modelService = modelService;
    }

    @GetMapping(value = "/listar")
	public List<M> listar() throws Exception {
        return modelService.findAll();
	}

    @GetMapping(value="/inserir")
    public List<Map<String, Object>> inserir() throws Exception {
        return modelService.criarNovaInstancia();
    }
    
    @PostMapping(value="/salvar")
    public void salvar(@Valid @RequestBody M instancia) {
        modelService.salvar(instancia);
    }

    @DeleteMapping(value="/excluir")
    public void excluir(@RequestParam("id") Integer id) {
        modelService.excluir(id);
    }
}
