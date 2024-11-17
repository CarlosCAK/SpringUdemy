package com.carlosjpa.libraryapi.Controller.dto;

import com.carlosjpa.libraryapi.model.Autor;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "campo obrigatório")
        @Size(max = 100,min = 2, message = "Campo fora do tamanho padrão")
        String nome,
        @NotNull(message = "campo obrigatório")
        @Past(message = "Nao pode ser uma data futura")
        LocalDate dataNascimento,
        @NotBlank(message ="campo obrigatório")
                @Size(max = 50, min = 1,message = "Campo fora do tamanho padrão")
        String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
