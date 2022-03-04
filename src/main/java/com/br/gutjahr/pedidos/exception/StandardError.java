package com.br.gutjahr.pedidos.exception;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer status;
    private String mensagem;

    public StandardError(Integer status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }
}
