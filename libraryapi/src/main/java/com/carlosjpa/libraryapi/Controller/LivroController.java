package com.carlosjpa.libraryapi.Controller;

import com.carlosjpa.libraryapi.Controller.dto.CadastroLivroDTO;
import com.carlosjpa.libraryapi.Controller.dto.ErroResposta;
import com.carlosjpa.libraryapi.Controller.dto.ResultadoPesquisaLivroDTO;
import com.carlosjpa.libraryapi.Controller.mappers.LivroMapper;
import com.carlosjpa.libraryapi.exceptions.RegistroDuplicadoException;
import com.carlosjpa.libraryapi.model.Livro;
import com.carlosjpa.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {

        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);
        URI location = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(location).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){

        return service.obterPorId(UUID.fromString(id)).map(livro -> {
            service.deletar(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }
}
