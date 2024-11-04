package com.carlos.produtosApi.repository;

import com.carlos.produtosApi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, String> {



}
