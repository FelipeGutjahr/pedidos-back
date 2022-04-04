package com.br.gutjahr.pedidos.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.br.gutjahr.pedidos.config.TenantContext;
import com.br.gutjahr.pedidos.exception.Advertencia;
import com.br.gutjahr.pedidos.model.app.CrudBaseModel;
import com.br.gutjahr.pedidos.model.managment.Usuario;
import com.br.gutjahr.pedidos.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class CrudBaseService<M extends CrudBaseModel<Integer>, 
        R extends JpaRepository<M, Integer>> {
    
    private Class<M> modelClass;
    @Autowired
    private R modelRepository;
    @Autowired
    private DatabaseSessionManager databaseSessionManager;
    @Autowired
    private TenantContext tenantContext;
    @Autowired
    private UsuarioRepository usuarioRepository;

    protected CrudBaseService() {
        this.modelClass = getModelClass();
    }

    protected void afterSave(M instancia) {
        instancia.afterSave();
    }

    protected void beforeSave(M instancia) {
        instancia.beforeSave();
    }

    public List<Map<String, Object>> criarNovaInstancia() throws IllegalAccessException, InstantiationException {
        List<Map<String, Object>> inputs = new ArrayList<>();
        Field[] allFields = modelClass.getDeclaredFields();
        for(int i=0;i<allFields.length;i++){
            Boolean jsonIgnore = false;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("label", allFields[i].getName());
            map.put("name", allFields[i].getName());
            map.put("type", allFields[i].getType().getSimpleName());
            
            Annotation[] anotations = allFields[i].getAnnotations();
            for(int j=0;j<anotations.length;j++) {
                if(anotations[j].annotationType().getSimpleName().equals("JsonIgnore"))
                    jsonIgnore = true;

                if(anotations[j].annotationType().getSimpleName().equals("NotNull"))
                    map.put("required", true);
            }
            if(anotations.length==0 || !map.containsKey("required")) {
                map.put("required", false);
            }
            if(!jsonIgnore && !allFields[i].getName().equals("id"))
                inputs.add(map);
        }
        return inputs;
    }

    public void excluir(Integer id) {
		modelRepository.deleteById(id);
	}

    public M getOne(Integer id) {
        return modelRepository.getOne(id);
    }

    public M visualizar(Integer id) {
        M instancia = getOne(id);
        if(instancia == null) {
            throw new Advertencia("Nenhum registro encontrado");
        }
        return instancia;
    }

    public List<M> findAll() {
        return modelRepository.findAll();
	}

    protected R getModelRepository() {
		return modelRepository;
	}

    @SuppressWarnings("unchecked")
    protected Class<M> getModelClass() {
        Class<?> actuClass = getClass();
        while (actuClass.getSuperclass() != CrudBaseService.class) {
            actuClass = actuClass.getSuperclass();
        }
        return (Class<M>) ((ParameterizedType) actuClass.getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void salvar(M instancia) {
        beforeSave(instancia);
        modelRepository.save(instancia);
    }

    public void alterarSchemaPorUsuarioId(Integer usuarioId) {
        Usuario usuario = usuarioRepository.getOne(usuarioId);
        if (usuario == null) {
            throw new Advertencia("Não foi possível encontrar um usuário com id: " + usuarioId);
        }
        databaseSessionManager.unbindSession();
        tenantContext.setSchema(usuario.getSchema());
        databaseSessionManager.bindSession();
    }
}
