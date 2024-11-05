package com.carlos.arqspring.arquiteturaspring.todos;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("todos")
public class TodoController {

    private TodoService todoService;
    // Spring entende também e instancia a classe
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public TodoEntity salvar(@RequestBody TodoEntity todo){
        try {
            return this.todoService.salvar(todo);

        }catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("{id}")
    public void atualizar(@PathVariable("id")Integer id,
                          @RequestBody TodoEntity todo){
        todo.setId(id);
        this.todoService.atualizarStatus(todo);
    }
    @GetMapping("{id}")
    public TodoEntity buscarPorId(@PathVariable("id")Integer id){
        return this.todoService.visualizarTarefa(id);
    }
    @DeleteMapping("{id}")
    public void remover(@PathVariable("id")Integer id){
        this.todoService.removerTodo(id);
    }
}
