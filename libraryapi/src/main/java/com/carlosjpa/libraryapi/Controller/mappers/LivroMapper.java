package com.carlosjpa.libraryapi.Controller.mappers;

import com.carlosjpa.libraryapi.Controller.dto.CadastroLivroDTO;
import com.carlosjpa.libraryapi.Controller.dto.ResultadoPesquisaLivroDTO;
import com.carlosjpa.libraryapi.model.Livro;
import com.carlosjpa.libraryapi.repository.AutorRepository;
import com.carlosjpa.libraryapi.repository.LivroRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    protected AutorRepository repository;

    @Mapping(target = "autor", expression = "java(repository.findById(dto.idAutor()).orElse(null))")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
