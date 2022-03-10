package com.br.gutjahr.pedidos.model.managment;

import com.br.gutjahr.pedidos.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
    @JsonInclude(Include.NON_NULL)
    @NotNull(message = "Infome o nome")
    private String nome;
    @JsonInclude(Include.NON_NULL)
    @NotNull(message = "Informe o e-mail")
    private String email;
    @JsonInclude(Include.NON_NULL)
    @NotNull(message = "Informe o telefone")
    private String telefone;
    @JsonInclude(Include.NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "data_cadastro")
    private Date dataCadastro;
    @JsonInclude(Include.NON_NULL)
    @Column(name = "is_restaurante")
    private Boolean isRestaurante;
    @JsonInclude(Include.NON_NULL)
    private Double avaliacao;
    @Transient
    @JsonIgnore
    private Perfil perfil;
    @NotNull(message = "Informe a senha")
    @JsonInclude(Include.NON_NULL)
    private String senha;
    @JsonIgnore
    private String schema;

    public Usuario() {
        this.perfil = Perfil.USER_FREE;
    }

    // USUARIO.LISTAR_RESTAURANTES
    public Usuario(Integer id, String nome, Double avaliacao) {
        this.id = id;
        this.nome = nome;
        this.avaliacao = avaliacao;
    }

    // UTILIZADO NO PROCESSO DE LOGIN, NÃO ALTERAR
    // USUARIO.FIND_BY_EMAIL
    public Usuario(Integer id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
}