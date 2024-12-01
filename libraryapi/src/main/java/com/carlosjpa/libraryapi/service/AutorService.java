package com.carlosjpa.libraryapi.service;

import com.carlosjpa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.carlosjpa.libraryapi.model.Autor;
import com.carlosjpa.libraryapi.repository.AutorRepository;
import com.carlosjpa.libraryapi.repository.LivroRepository;
import com.carlosjpa.libraryapi.security.SecurityService;
import com.carlosjpa.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {


    private final AutorRepository autorRepository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;


    public Autor salvar(Autor autor) {
        validator.validar(autor);
        autor.setUsuario(securityService.obterUsuarioLogado());
        return autorRepository.save(autor);
    }public void atualizar(Autor autor) {
        validator.validar(autor);
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessario que o autor ja esteja salvo na base");
        }
         autorRepository.save(autor);
    }
    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }
    public void deletar(Autor autor) {
        if (possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Não é possível excluir um autor que possui livros cadastrados");
        }
        autorRepository.delete(autor);
    }
    public List<Autor> pesquisarPorNome(String nome, String nacionalidade) {
        if(nome != null && nacionalidade != null) {
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
        if (nome != null) {
            return autorRepository.findByNome(nome);
        }
        if (nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }
        return autorRepository.findAll();
    }
    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher.matching().
                withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor,matcher);

        return autorRepository.findAll(autorExample);
    }
    public boolean possuiLivro(Autor autor){
        
        return livroRepository.existsByAutor(autor);

    }


}
