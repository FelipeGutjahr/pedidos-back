package com.br.gutjahr.pedidos.enums;

import lombok.Getter;
import lombok.Setter;

public enum EFormaPag {

    DINHEIRO("D", "Dinheiro"),CARTAO("C", "Cartão");

    @Getter
    @Setter
    private String forma;
    @Getter
    @Setter
    private String descricao;

    EFormaPag(String forma, String descricao) {
        this.forma = forma;
        this.descricao = descricao;
    }

    public static EFormaPag toEnum(String forma) {
        if(forma == null){
            return null;
        }

        for(EFormaPag x : EFormaPag.values()){
            if(forma.equals(x.getForma())){
                return x;
            }
        }

        throw new IllegalArgumentException("Forma de pagamento inválida: " + forma);
    }
}