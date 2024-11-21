package com.carlosjpa.libraryapi.Controller;

import com.carlosjpa.libraryapi.Controller.dto.CadastroLivroDTO;
import com.carlosjpa.libraryapi.Controller.dto.ErroResposta;
import com.carlosjpa.libraryapi.Controller.dto.ResultadoPesquisaLivroDTO;
import com.carlosjpa.libraryapi.Controller.mappers.LivroMapper;
import com.carlosjpa.libraryapi.exceptions.AutorNaoEncontadoException;
import com.carlosjpa.libraryapi.exceptions.RegistroDuplicadoException;
import com.carlosjpa.libraryapi.model.Autor;
import com.carlosjpa.libraryapi.model.GeneroLivro;
import com.carlosjpa.libraryapi.model.Livro;
import com.carlosjpa.libraryapi.repository.AutorRepository;
import com.carlosjpa.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;
    private final AutorRepository autorRepository;

    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {

        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);
        URI location = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(location).build();

    }

    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){

        return service.obterPorId(UUID.fromString(id)).map(livro -> {
            service.deletar(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }
    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    @GetMapping
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> listar(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "nome-autor" , required = false) String nomeAutor,
            @RequestParam(value = "genero" , required = false) GeneroLivro genero,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "ano-publicacao", required = false) Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
            ){

        Page<Livro> paginaResultado = service.pesquisa(isbn,titulo,nomeAutor,genero,anoPublicacao,pagina,tamanhoPagina);

        Page<ResultadoPesquisaLivroDTO> paginaResultadoDto = paginaResultado.map(mapper::toDTO);


        return ResponseEntity.ok(paginaResultadoDto);
    }
    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") @Valid String id,
                                          @RequestBody @Valid CadastroLivroDTO dto ){

        var optionalLivro = service.obterPorId(UUID.fromString(id));
        if(optionalLivro.isEmpty()) {
            ResponseEntity.notFound().build();
        }

        Livro livro = optionalLivro.get();

        livro.setTitulo(dto.titulo());
        livro.setIsbn(dto.isbn());
        livro.setDataPublicacao(dto.dataPublicacao());
        livro.setGenero(dto.genero());
        livro.setPreco(dto.preco());
        livro.setAutor(autorRepository.findById(dto.idAutor()).orElseThrow
                ( () -> {
                    throw new AutorNaoEncontadoException("O autor informado não existe na base de dados");
                }));
        service.atualizar(livro);

        return ResponseEntity.noContent().build();
    }

}
