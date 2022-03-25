package com.br.gutjahr.pedidos.model.app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "endereco")
@JsonInclude(Include.NON_NULL)
public class Endereco extends CrudBaseModel<Integer> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Informe a rua")
    private String rua;
    @NotNull(message = "Informe o número")
    private String numero;
    @NotNull(message = "Informe o bairro")
    private String bairro;
    private String cep;

    public Endereco() {}
}
