package com.carlosjpa.libraryapi.service;

import com.carlosjpa.libraryapi.model.Autor;
import com.carlosjpa.libraryapi.model.GeneroLivro;
import com.carlosjpa.libraryapi.model.Livro;
import com.carlosjpa.libraryapi.repository.AutorRepository;
import com.carlosjpa.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("d2042a9b-ec95-4567-bb35-6cd06a9107a4")).orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024,6,1));

    }

    @Transactional
    public void executar(){
    // Salva autor
        Autor autor = new Autor();
        autor.setNome("Joao");
        autor.setNascionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1950,1,31));
        autorRepository.save(autor);


        // Salva o livro
        Livro l1 = new Livro();
        l1.setTitulo("Outro livro teste");
        l1.setIsbn("1231-3123");
        l1.setPreco(BigDecimal.valueOf(100));
        l1.setGenero(GeneroLivro.FICCAO);
        l1.setDataPublicacao(LocalDate.of(1980, 1, 2));



        l1.setAutor(autor);

        livroRepository.save(l1);

        if(autor.getNome().equals("José")){
            throw new RuntimeException("Rollback");
        }
    }

}
