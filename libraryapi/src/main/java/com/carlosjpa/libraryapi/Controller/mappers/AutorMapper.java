package com.carlosjpa.libraryapi.Controller.mappers;

import com.carlosjpa.libraryapi.Controller.dto.AutorDTO;
import com.carlosjpa.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "nome", target = "nome")
    Autor toEntity(AutorDTO dto);

    AutorDTO toDto(Autor entity);


}
