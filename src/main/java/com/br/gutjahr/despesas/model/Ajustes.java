package com.br.gutjahr.despesas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "ajustes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ajustes implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "primeiro_dia_mes")
    private Integer primeiroDiaMes;

    public Ajustes(){}

    public Ajustes(Integer id, Integer primeiroDiaMes) {
        this.id = id;
        this.primeiroDiaMes = primeiroDiaMes;
    }
}