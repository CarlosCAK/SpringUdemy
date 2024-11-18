package com.carlosjpa.libraryapi.Controller;

import com.carlosjpa.libraryapi.Controller.dto.AutorDTO;
import com.carlosjpa.libraryapi.Controller.dto.ErroResposta;
import com.carlosjpa.libraryapi.Controller.mappers.AutorMapper;
import com.carlosjpa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.carlosjpa.libraryapi.exceptions.RegistroDuplicadoException;
import com.carlosjpa.libraryapi.model.Autor;
import com.carlosjpa.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
//localhost:8080/autores
public class AutorController implements GenericController {


    private final AutorService autorService;
    private final AutorMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) {

        var autor = mapper.toEntity(dto);
        autorService.salvar(autor);
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return autorService.obterPorId(idAutor).map(autor -> {
            var dto = mapper.toDto(autor);
            return ResponseEntity.ok(dto);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = this.autorService.obterPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        this.autorService.deletar(autorOptional.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<AutorDTO>> pesquisar
            (@RequestParam(value = "nome", required = false) String nome,
             @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);

        List<AutorDTO> lista = resultado.stream().map(mapper::toDto).toList();

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = this.autorService.obterPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Autor autor = autorOptional.get();

        autor.setNome(dto.nome());
        autor.setDataNascimento(dto.dataNascimento());
        autor.setNacionalidade(dto.nacionalidade());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();
    }


}
