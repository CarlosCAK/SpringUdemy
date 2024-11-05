package com.carlos.arqspring.arquiteturaspring.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {


    private ToDoRepository toDoRepository;
    private TodoValidator todoValidator;
    private MailSender mailSender;

    /*
    Pela delcaração do todoresposutory no construtor o spring ja entende
    que é uma dependencia do todoService e precisa injetar isso então
    nao precisamos utilizar o autowired
     */

    public TodoService(ToDoRepository toDoRepository, TodoValidator todoValidator, MailSender mailSender) {
        this.toDoRepository = toDoRepository;
        this.todoValidator = todoValidator;
        this.mailSender = mailSender;
    }

    public TodoEntity salvar(TodoEntity novoTodo) {
        todoValidator.validar(novoTodo);
        return toDoRepository.save(novoTodo);
    }
    public void atualizarStatus(TodoEntity todo) {
        toDoRepository.save(todo);
        String status = todo.getConcluido() == Boolean.TRUE ? "Concluido" : "Não Concluido";
        mailSender.enviarMensagem("Todo " + todo.getDescricao() +  "foi atualizado para" + status);
    }
    public TodoEntity visualizarTarefa(Integer id) {
        return  this.toDoRepository.findById(id).orElse(null);
    }
    public void removerTodo(Integer id) {
        toDoRepository.deleteById(id);
    }
}
