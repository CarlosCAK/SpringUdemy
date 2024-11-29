package com.carlosjpa.libraryapi.security;

import com.carlosjpa.libraryapi.model.Usuario;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class CustomAuthentication implements Authentication {

    private final Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.usuario.getRoles().
                stream().
                map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return this.usuario;
    }

    @Override
    public Object getPrincipal() {
        return this.usuario;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }
    // É o login do usuário
    @Override
    public String getName() {
        return usuario.getLogin();
    }
}
