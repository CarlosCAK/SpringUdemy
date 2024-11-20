package com.carlosjpa.libraryapi.Controller.mappers;

import com.carlosjpa.libraryapi.Controller.dto.UsuarioDTO;
import com.carlosjpa.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO toDto(Usuario usuario);

    Usuario toEntity(UsuarioDTO usuarioDTO);
}
