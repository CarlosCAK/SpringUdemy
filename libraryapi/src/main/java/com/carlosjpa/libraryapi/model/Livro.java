package com.carlosjpa.libraryapi.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro",schema = "public")
public class Livro {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150,nullable = false)
    private String titulo;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Column(name = "preco", precision = 18, scale = 2)
    private double preco;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero",nullable = false)
    private GeneroLivro genero;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;
}
