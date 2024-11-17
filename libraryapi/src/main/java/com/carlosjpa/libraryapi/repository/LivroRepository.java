package com.carlosjpa.libraryapi.repository;

import com.carlosjpa.libraryapi.model.Autor;
import com.carlosjpa.libraryapi.model.GeneroLivro;
import com.carlosjpa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    List<Livro> findAllByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);
    List<Livro> findByIsbn(String titulo);

    List<Livro> findByIsbnAndTitulo(String isbn, String titulo);

    @Query("select l from Livro as l order by l.titulo, l.preco")
    List<Livro> buscarTodos();

    @Query("select a from Livro as l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    @Query("select distinct  l from Livro as l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
        select l.genero
        from Livro as l
        join l.autor a 
        where a.nacionalidade = 'Brasileira'
        order by l.genero
        """)
    List<String> listarGenerosAutoresBrasileiros();

    @Query("select l from Livro as l where l.genero = :genero order by :paramOrdenacao asc ")
    List<Livro> findByGenero(@Param("genero") GeneroLivro genero, @Param("paramOrdenacao") String nomePropriedade);

    @Query("select l from Livro as l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionParameters(GeneroLivro genero,String nomePropriedade);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate datePublicacao);

    boolean existsByAutor(Autor autor);
}
