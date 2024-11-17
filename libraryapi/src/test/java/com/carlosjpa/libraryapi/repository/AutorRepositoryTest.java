package com.carlosjpa.libraryapi.repository;

import com.carlosjpa.libraryapi.model.Autor;
import com.carlosjpa.libraryapi.model.GeneroLivro;
import com.carlosjpa.libraryapi.model.Livro;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Transient;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("José");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1950,1,31));
        var autorSalvo = autorRepository.save(autor);
        System.out.println("Autor salvo"+ autorSalvo);
    }

    @Test
    public void atualizarTest(){
        Optional<Autor> autor = autorRepository.findById(UUID.fromString("f05cc8e3-9c5e-4795-81de-6721c8acef68"));

        if (autor.isPresent()){
            Autor autorEncontrado = autor.get();
            autorEncontrado.setDataNascimento(LocalDate.of(1980,1,31));

            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = autorRepository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores" + autorRepository.count());
    }
    @Test
    public void deletarPorIdTest(){
        autorRepository.deleteById(UUID.fromString("f05cc8e3-9c5e-4795-81de-6721c8acef68"));

        Optional<Autor> autor = autorRepository.findById(UUID.fromString("f05cc8e3-9c5e-4795-81de-6721c8acef68"));

        Assertions.assertFalse(autor.isPresent());
    }
    @Test
    public void deleteTest(){
        var id = UUID.fromString("9ae5fcbb-58f1-47e3-9c66-1ace445f0ffd");
        var maria = autorRepository.findById(id).get();
        autorRepository.delete(maria);
    }
    @Test
    public void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Marcos");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1970,12,11));
        Livro l1 = new Livro();
        l1.setTitulo("Ciencia infinita");
        l1.setIsbn("1234-3112");
        l1.setPreco(BigDecimal.valueOf(204));
        l1.setGenero(GeneroLivro.CIENCIA);
        l1.setDataPublicacao(LocalDate.of(1999, 1, 2));
        l1.setAutor(autor);
        Livro l2 = new Livro();
        l2.setTitulo("Ciencia sem fim");
        l2.setIsbn("12314-3112");
        l2.setPreco(BigDecimal.valueOf(120));
        l2.setGenero(GeneroLivro.CIENCIA);
        l2.setDataPublicacao(LocalDate.of(2000, 1, 2));
        l2.setAutor(autor);

        autor.setLivros(new ArrayList<Livro>());

        autor.getLivros().add(l1);
        autor.getLivros().add(l2);

        autorRepository.save(autor);
    }
    @Test
    @Transactional
    public void listarLivrosAutor(){
        var id = UUID.fromString("c3b54a89-d576-43c7-a71e-4e4907914863");
        Autor autor = autorRepository.findById(id).get();

        // buscar os livros do autor

        System.out.println(livroRepository.findAllByAutor(autor));
    }


}
