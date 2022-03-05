package com.br.gutjahr.pedidos.model.app;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "endereco", schema = "public")
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
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
