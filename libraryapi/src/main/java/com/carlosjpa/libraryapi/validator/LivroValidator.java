package com.carlosjpa.libraryapi.validator;

import com.carlosjpa.libraryapi.exceptions.CampoInvalidoException;
import com.carlosjpa.libraryapi.exceptions.LivroNaoSalvoException;
import com.carlosjpa.libraryapi.exceptions.RegistroDuplicadoException;
import com.carlosjpa.libraryapi.model.Livro;
import com.carlosjpa.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository livroRepository;
    private static final int  ANO_EXIGENCIA_PRECO = 2020;

    public void validar(Livro livro) {
        if(existeComMesmoISBN(livro)){
            throw new RegistroDuplicadoException("ISBN Duplicado");
        }
        if(isPrecoObrigatorio(livro)){
            throw new CampoInvalidoException("preco", "Para livros com ano de 2020 o ano é obrigatorio");
        }
    }

    private boolean isPrecoObrigatorio(Livro livro) {
        return livro.getPreco() == null &&  livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeComMesmoISBN(Livro livro){

        var livroEncontrado = livroRepository.findByIsbn(livro.getIsbn());
        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }
        return livroEncontrado.map(Livro::getId).
                stream().anyMatch(id -> !id.equals(livro.getId()));
    }

}
