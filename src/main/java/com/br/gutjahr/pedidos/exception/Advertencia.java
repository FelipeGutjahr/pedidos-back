package com.br.gutjahr.pedidos.exception;

public class Advertencia extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public Advertencia() {
        super();
    }

    public Advertencia(String mensagem){
        super(mensagem);
    }
}
