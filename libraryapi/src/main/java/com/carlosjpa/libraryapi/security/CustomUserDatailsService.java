package com.carlosjpa.libraryapi.security;

import com.carlosjpa.libraryapi.model.Usuario;
import com.carlosjpa.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDatailsService implements UserDetailsService {

    private final UsuarioService  usuarioService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Usuario usuario = usuarioService.obterPorLogin(login);

        if(usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }



        return User.builder().
                username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
                .build();
    }
}
