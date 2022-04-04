package com.br.gutjahr.pedidos.model.managment;

import com.br.gutjahr.pedidos.enums.Perfil;
import com.br.gutjahr.pedidos.model.app.CrudBaseModel;
import com.br.gutjahr.pedidos.model.app.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "usuario", schema = "managment")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Usuario extends CrudBaseModel<Integer> {
    private static final long serialVersionUID = 1L;

    @Id
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
    @Column(name = "is_restaurante")
    private Boolean isRestaurante;
    @Column(name = "img_url")
    private String imgUrl;
    @Transient
    private List<Item> itens;
    @Transient
    @JsonIgnore
    private Perfil perfil;
    @NotNull(message = "Informe a senha")
    private String senha;
    @JsonIgnore
    private String schema;

    public Usuario() {
        this.perfil = Perfil.CLIENTE;
    }

    // RESTAURANTE.FIND_ALL
    public Usuario(Integer id, String nome, String imgUrl) {
        this.id = id;
        this.nome = nome;
        this.imgUrl = imgUrl;
    }

    // UTILIZADO NO PROCESSO DE LOGIN/DEFINIR SCHEMA DAS REQUSIÇÕES, NÃO ALTERAR
    // USUARIO.FIND_BY_EMAIL
    public Usuario(Integer id, String nome, String email, String senha, String schema) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.schema = schema;
    }

    // RESTAURANTE.GET_ONE
    // USUARIO.GET_ONE
    public Usuario(Integer id, String nome, String email, String telefone, Date dataCadatro, String imgUrl, String schema) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = dataCadatro;
        this.imgUrl = imgUrl;
        this.schema = schema;
    }
}