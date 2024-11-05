package com.carlos.arqspring.arquiteturaspring.todos;

import org.springframework.stereotype.Component;

@Component
public class MailSender {

    public void enviarMensagem(String mensagem){
        System.out.println("Enviando mensagem: "+mensagem);
    }

}
