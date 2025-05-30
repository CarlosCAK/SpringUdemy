package com.carlosjpa.libraryapi.Controller;


import com.carlosjpa.libraryapi.model.Client;
import com.carlosjpa.libraryapi.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("permitAll()")
    public void salvar(@RequestBody Client client) {
        service.salvar(client);
    }
}
