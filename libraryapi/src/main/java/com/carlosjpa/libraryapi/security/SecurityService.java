package com.carlosjpa.libraryapi.security;

import com.carlosjpa.libraryapi.model.Usuario;
import com.carlosjpa.libraryapi.repository.UsuarioRepository;
import com.carlosjpa.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;


    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof  CustomAuthentication customAuth){
            return customAuth.getUsuario();
        }
        return null;
    }
}
