package com.carlosjpa.libraryapi.repository;

import com.carlosjpa.libraryapi.model.Autor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("José");
        autor.setNascionalidade("Brasileira");
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

}
