package com.br.gutjahr.pedidos.model.app;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "item")
public class Item extends CrudBaseModel<Integer> {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Informe o nome")
    private String nome;
    @NotNull(message = "Informe o preço")
    private Double preco;
    @NotNull(message = "Informe a decrição")
    private String descricao;

    public Item() {}

    public Item(Integer id, String nome, Double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    // ITEM.GET_ONE
    public Item(Integer id, String nome, Double preco, String descricao) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }
}
