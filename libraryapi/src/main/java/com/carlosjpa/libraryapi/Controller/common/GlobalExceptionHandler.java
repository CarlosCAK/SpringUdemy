package com.carlosjpa.libraryapi.Controller.common;

import com.carlosjpa.libraryapi.Controller.dto.ErroCampo;
import com.carlosjpa.libraryapi.Controller.dto.ErroResposta;
import com.carlosjpa.libraryapi.exceptions.*;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldError = e.getFieldErrors();
        List<ErroCampo> listaDeErros =
                fieldError.stream().map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage())).toList();


        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação", listaDeErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        return ErroResposta.respostaPadrao(e.getMessage());
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAccessDeniedException(AccessDeniedException e){
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), "Acesso negado", List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrorNaoTratados(RuntimeException e ){
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado, entre em contato com a administracao",
                List.of());
    }
    @ExceptionHandler(AutorNaoEncontadoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleAutorNaoEncontadoException(AutorNaoEncontadoException e){
        return new ErroResposta(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(LivroNaoSalvoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleLivroNaoSalvoException(LivroNaoSalvoException e){
        return new ErroResposta(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoException(CampoInvalidoException e){


        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(),
                List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleUsernameNotFoundException(UsernameNotFoundException e){
        return new ErroResposta(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }


}
