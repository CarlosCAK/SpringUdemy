package com.carlosjpa.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Getter
@Setter
@ToString
public class Autor {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;
    @Column(name = "nacionalidade",length = 50, nullable = false)
    private String nascionalidade;

    @OneToMany(mappedBy = "autor")
    private List<Livro> livros;

}
