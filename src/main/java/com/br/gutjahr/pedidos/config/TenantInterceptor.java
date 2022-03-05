package com.br.gutjahr.pedidos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.br.gutjahr.pedidos.model.managment.Usuario;
import com.br.gutjahr.pedidos.repository.UsuarioRepository;
import com.br.gutjahr.pedidos.security.JWTUtil;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Autowired
    TenantContext tenantContext;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    UsuarioRepository repository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            String user = jwtUtil.getUsername(token.replace("Bearer ", ""));
            Usuario usuario = repository.findByEmail(user);
            tenantContext.setSchema(usuario.getSchema());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        tenantContext.clean();
    }
}
