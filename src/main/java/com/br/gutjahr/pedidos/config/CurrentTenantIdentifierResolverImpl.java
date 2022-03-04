package com.br.gutjahr.pedidos.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    @Autowired
    TenantContext context;

    @Override
    public String resolveCurrentTenantIdentifier() {
        return context.getSchema();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
