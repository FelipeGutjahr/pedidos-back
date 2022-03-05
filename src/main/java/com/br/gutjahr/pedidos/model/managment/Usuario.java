package com.br.gutjahr.pedidos.model.managment;

import com.br.gutjahr.pedidos.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "usuario", schema = "managment")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Infome o nome")
    private String nome;
    @NotNull(message = "Informe o e-mail")
    private String email;
    @NotNull(message = "Informe o telefone")
    private String telefone;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_cadastro")
    private Date dataCadastro;
    @Transient
    @JsonIgnore
    private Perfil perfil;
    //@JsonIgnore()
    @NotNull(message = "Informe a senha")
    private String senha;

    @JsonIgnore
    private String schema;

    public Usuario() {
        this.perfil = Perfil.CLIENTE;
    }
}