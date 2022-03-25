package com.br.gutjahr.pedidos.model.app;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "item")
@JsonInclude(Include.NON_NULL)
public class Item extends CrudBaseModel<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Informe o nome")
    private String nome;
    @NotNull(message = "Informe o preço")
    private Double preco;
    @NotNull(message = "Informe a decrição")
    private String descricao;
    @Column(name = "img_url")
    private String imgUrl;

    public Item() {}

    // ITEM.GET_ONE
    public Item(Integer id, String nome, Double preco, String descricao) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }
}
