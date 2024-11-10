package com.carlosjpa.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Getter
@Setter
public class Autor {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "id", length = 100, nullable = false)
    private String nome;
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;
    @Column(name = "nacionalidade",length = 50, nullable = false)
    private String nascionalidade;

    @OneToMany(mappedBy = "autor")
    private List<Livro> livros;

}
