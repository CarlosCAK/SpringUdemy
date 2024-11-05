package com.carlos.arqspring.arquiteturaspring.todos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<TodoEntity,Integer> {


    boolean existsByDescricao(String descricao);
}
