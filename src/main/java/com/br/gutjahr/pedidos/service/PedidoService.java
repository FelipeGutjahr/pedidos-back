package com.br.gutjahr.pedidos.service;

import com.br.gutjahr.pedidos.config.TenantContext;
import com.br.gutjahr.pedidos.exception.Advertencia;
import com.br.gutjahr.pedidos.model.app.Pedido;
import com.br.gutjahr.pedidos.model.managment.Usuario;
import com.br.gutjahr.pedidos.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService extends CrudBaseService<Pedido, PedidoRepository> {

    @Autowired
    private DatabaseSessionManager databaseSessionManager;
    @Autowired
    private TenantContext tenantContext;
    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private UsuarioService usuarioService;

    private void alterarSchemaParaRestaurante() {
        Usuario restaurante = restauranteService.getOne(13);
        databaseSessionManager.unbindSession();
        tenantContext.setSchema(restaurante.getSchema());
        databaseSessionManager.bindSession();
    }

    @Override
    protected void beforeSave(Pedido pedido) {
        if(pedido.getItens().isEmpty()) {
            throw new Advertencia("Informe os itens");
        }
        alterarSchemaParaRestaurante();
        pedido.setCliente(usuarioService.buscar());
        if(pedido.getCliente().isEmpty()) {
            throw new Advertencia("Não foi possível obter o usuário atual");
        }
        pedido.beforeSave();
    }
}
