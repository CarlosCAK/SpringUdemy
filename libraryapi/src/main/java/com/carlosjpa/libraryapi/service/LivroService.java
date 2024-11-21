package com.carlosjpa.libraryapi.service;

import com.carlosjpa.libraryapi.Controller.dto.CadastroLivroDTO;
import com.carlosjpa.libraryapi.exceptions.LivroNaoSalvoException;
import com.carlosjpa.libraryapi.model.GeneroLivro;
import com.carlosjpa.libraryapi.model.Livro;
import com.carlosjpa.libraryapi.model.Usuario;
import com.carlosjpa.libraryapi.repository.LivroRepository;
import com.carlosjpa.libraryapi.repository.Specs.LivroSpecs;
import com.carlosjpa.libraryapi.security.SecurityService;
import com.carlosjpa.libraryapi.validator.LivroValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.carlosjpa.libraryapi.repository.Specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidator validator;
    private final SecurityService  securityService;

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Livro livro) {
        repository.delete(livro);
    }
    public Page<Livro> pesquisa(String isbn,
                                String titulo,
                                String nomeAutor,
                                GeneroLivro genero,
                                Integer anoPublicacao,
                                Integer pagina,
                                Integer tamanhoPagina) {
//        Specification<Livro> specs = Specification.
//                where(LivroSpecs.isbnEqual(isbn))
//                .and(LivroSpecs.tituloLike(titulo))
//                .and(LivroSpecs.generoEqual(genero));

        //Basicamente um where 0 = 0
        Specification<Livro> specs = Specification.where(((root, query, criteriaBuilder) -> criteriaBuilder.conjunction()));

        if (isbn != null) {
            // query = query and isbn = :isbn
            specs = specs.and(isbnEqual(isbn));
        }
        if (titulo != null) {
            specs = specs.and(tituloLike(titulo));
        }
        if(genero != null){
            specs = specs.and(generoEqual(genero));
        }
        if(anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }
        if(nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        Pageable pageableRequest = PageRequest.of(pagina,tamanhoPagina);
        
        return repository.findAll(specs, pageableRequest);
    }

    public void atualizar(Livro livro) {
        if(livro.getId() == null){
            throw new LivroNaoSalvoException("O livro não está salvo na base de dados");
        }
        validator.validar(livro);
        repository.save(livro);
    }
}
