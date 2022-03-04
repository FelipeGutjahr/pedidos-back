package com.br.gutjahr.despesas.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.br.gutjahr.despesas.model.CrudBaseModel;
import com.br.gutjahr.despesas.service.CrudBaseService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Page<M> listar(Pageable pageable) throws Exception {
        return modelService.findAll(pageable);
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