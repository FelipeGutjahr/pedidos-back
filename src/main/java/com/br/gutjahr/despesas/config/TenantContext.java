package com.br.gutjahr.despesas.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class TenantContext {

    @Getter
    private final String MANAGMENT = "managment";

    @Getter
    @Setter
    private String schema;

    public void clean() {
        schema = MANAGMENT;
    }

    public TenantContext() {
        this.schema = MANAGMENT;
    }

}
