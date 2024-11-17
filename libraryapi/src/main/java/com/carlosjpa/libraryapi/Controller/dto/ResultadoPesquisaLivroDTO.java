package com.carlosjpa.libraryapi.Controller.dto;

import com.carlosjpa.libraryapi.model.Autor;
import com.carlosjpa.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivroDTO(UUID id,
                                        String isbn,
                                        String titulo,
                                        LocalDate dataPublicacao,
                                        GeneroLivro genero,
                                        BigDecimal preco,
                                        AutorDTO autor) {
}
