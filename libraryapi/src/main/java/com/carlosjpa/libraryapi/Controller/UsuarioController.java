package com.carlosjpa.libraryapi.Controller;

import com.carlosjpa.libraryapi.Controller.dto.UsuarioDTO;
import com.carlosjpa.libraryapi.Controller.mappers.UsuarioMapper;
import com.carlosjpa.libraryapi.model.Usuario;
import com.carlosjpa.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        var usuario =  mapper.toEntity(usuarioDTO);
        service.salvar(usuario);
    }
}
