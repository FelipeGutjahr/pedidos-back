package com.br.gutjahr.pedidos.enums;

import lombok.Getter;
import lombok.Setter;

public enum EFormaEntrega {
    
    DELIVERY("D", "Delivery"),RETIRADA("R", "Retirada");

    @Getter
    @Setter
    private String forma;
    @Getter
    @Setter
    private String descricao;

    EFormaEntrega(String forma, String descricao) {
        this.forma = forma;
        this.descricao = descricao;
    }

    public static EFormaEntrega toEnum(String forma) {
        if(forma == null){
            return null;
        }

        for(EFormaEntrega x : EFormaEntrega.values()) {
            if(forma.equals(x.getForma())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Forma de entrega inválida: " + forma);
    }
}
