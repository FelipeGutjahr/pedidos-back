package com.br.gutjahr.pedidos.model.app;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cartao")
@JsonInclude(Include.NON_NULL)
public class Cartao extends CrudBaseModel<Integer> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="nome_no_cartao")
    @NotNull(message="Informe o nome no cartão")
    private String nomeNoCartao;
    @NotNull(message="Informe o número")
    @Size(max=16,min=16,message="Número precisa ter 16 caracteres")
    private String numero;
    @NotNull(message="Informe o vencimento")
    @Column(name="vencimento")
    private String vencimento;
    @Size(max=3,min=3,message="CVC precisa ter 3 caracteres")
    @NotNull(message="Informe o CVC")
    private String cvc;

    public Cartao() {}
}
