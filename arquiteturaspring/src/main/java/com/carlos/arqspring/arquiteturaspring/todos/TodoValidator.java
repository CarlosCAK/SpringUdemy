package com.carlos.arqspring.arquiteturaspring.todos;

import org.springframework.stereotype.Component;

@Component
public class TodoValidator {

    private ToDoRepository repository;

    public TodoValidator(ToDoRepository repository) {
        this.repository = repository;
    }
    public void validar(TodoEntity todo){
        if(existeTodoComDescricao(todo.getDescricao())){
            throw new IllegalArgumentException("Já existe um todo com essa descrição");
        }
    }

    private boolean existeTodoComDescricao(String descricao){
        return this.repository.existsByDescricao(descricao);
    }
}
