package com.carlosjpa.libraryapi.repository;

import com.carlosjpa.libraryapi.model.Autor;
import com.carlosjpa.libraryapi.model.GeneroLivro;
import com.carlosjpa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Transient;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
        l1.setGenero(GeneroLivro.MISTERIO);
        l1.setDataPublicacao(LocalDate.of(1980, 1, 2));

    Autor autor = autorRepository.findById(UUID.fromString("3cfcf6a2-6e40-4809-9b34-551816dc9957")).orElse(null);

        l1.setAutor(autor);

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
    @Test
    public void atualizarAutorDoLivro(){
        UUID id = UUID.fromString("ccfb3bc3-14d8-4ca2-9a49-e45f37ba8ef0");
        var livro = livroRepository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("d801025e-1e38-42ea-a5e9-d4c39fb0d1b2");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }
    @Test
    public void deletar(){
        UUID id = UUID.fromString("0705a2af-cbae-4e1a-b721-8a0f084eb18f");
        livroRepository.deleteById(id);
    }
    @Test
    @Transactional
    public void buscarLivroTeste(){
        UUID id = UUID.fromString("a982fb03-b288-4e2a-b80a-0fb6cb761156");
        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println("livro");
        System.out.println(livro.getTitulo());

        System.out.println("Autor");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> byTitulo = livroRepository.findByTitulo("Ciencia infinita");
        byTitulo.forEach(System.out::println);
    }
    @Test
    void pesquisaPorIsbnTest(){
        List<Livro> byTitulo = livroRepository.findByIsbn("1234-3112");
        byTitulo.forEach(System.out::println);
    }
    @Test
    void pesquisaPorIsbnETituloTest(){
        List<Livro> byTitulo = livroRepository.findByIsbnAndTitulo("1234-3112", "Ciencia infinita");
        byTitulo.forEach(System.out::println);
    }
    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = livroRepository.buscarTodos();
        resultado.forEach(System.out::println);
    }
    @Test
    void listarAutoresDosLivros(){
        var resultado = livroRepository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }@Test
    void listarGenerosLivrosAutoresBrasileiros(){
        var resultado = livroRepository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParam(){
        var resultado = livroRepository.findByGenero(GeneroLivro.CIENCIA, "dataPublicacao");
        resultado.forEach(System.out::println);
    }
    @Test
    void deletePorGeneroTest(){
        livroRepository.deleteByGenero(GeneroLivro.MISTERIO);
    }
    @Test
    void updateDataPublicacaoTest(){
        livroRepository.updateDataPublicacao(LocalDate.of(1980, 1, 2));
    }




}