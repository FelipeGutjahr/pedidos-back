package com.br.gutjahr.despesas.model;

import com.br.gutjahr.despesas.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "usuario", schema = "public")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_cadastro")
    private Date dataCadastro;
    @Column(name = "img_url")
    private String imgUrl;
    @Transient
    @JsonIgnore
    private Perfil perfil;
    //@JsonIgnore
    private String senha;

    @JsonIgnore
    private String schema;

    public Usuario() {
        this.perfil = Perfil.USER_FREE;
    }

    public Usuario(Integer id, String nome, String email, Date dataCadastro, String imgUrl, String senha, String driverClassName, String url, String username, String password, Boolean initialize, String schema) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataCadastro = dataCadastro;
        this.imgUrl = imgUrl;
        this.senha = senha;
        this.schema = schema;
    }
}