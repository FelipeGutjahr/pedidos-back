package com.br.gutjahr.pedidos.enums;

import lombok.Getter;

@Getter
public enum Perfil {

    CLIENTE(1, "CLIENTE");

    private int cod;
    private String descricao;

    Perfil(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static Perfil toEnum(Integer cod) {
        if(cod == null){
            return null;
        }

        for(Perfil x : Perfil.values()){
            if(cod.equals(x.getCod())){
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
