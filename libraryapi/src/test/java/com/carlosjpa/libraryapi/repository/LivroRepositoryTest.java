package com.carlosjpa.libraryapi.repository;

import com.carlosjpa.libraryapi.model.Autor;
import com.carlosjpa.libraryapi.model.GeneroLivro;
import com.carlosjpa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro l1 = new Livro();
        l1.setTitulo("Outro livro teste");
        l1.setIsbn("1231-3123");
        l1.setPreco(BigDecimal.valueOf(100));
        l1.setGenero(GeneroLivro.FICCAO);
        l1.setDataPublicacao(LocalDate.of(1980, 1, 2));

//     Autor autor = autorRepository.findById(UUID.fromString("7ad2a74a-75b1-413a-bdb4-84f5a9cff301")).orElse(null);

        l1.setAutor(new Autor());

        livroRepository.save(l1);
    }

    @Test
    void salvarAutorELivroTeste(){
        Livro l1 = new Livro();
        l1.setTitulo("Outro livro teste");
        l1.setIsbn("1231-3123");
        l1.setPreco(BigDecimal.valueOf(100));
        l1.setGenero(GeneroLivro.FICCAO);
        l1.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Joao");
        autor.setNascionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1950,1,31));

        autorRepository.save(autor);

        l1.setAutor(autor);

        livroRepository.save(l1);
    }

    @Test
    void salvarCascadeTest(){
        Livro l1 = new Livro();
        l1.setTitulo("Outro livro teste");
        l1.setIsbn("1231-3123");
        l1.setPreco(BigDecimal.valueOf(100));
        l1.setGenero(GeneroLivro.FICCAO);
        l1.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Joao");
        autor.setNascionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1950,1,31));

        l1.setAutor(autor);

        livroRepository.save(l1);
    }

}