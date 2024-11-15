package com.carlosjpa.libraryapi.repository;

import com.carlosjpa.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;
    @Autowired
    private TransacaoService transacaoService;

    @Test
    public void transacaoSimples() {
        transacaoService.executar();
    }

    @Test
    void transacaoEstadoManaged(){
        transacaoService.atualizacaoSemAtualizar();
        
    }
}
