package com.carlosjpa.libraryapi.Controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
            @NotBlank(message = "Campo obrigatório")
            String login,
            @NotBlank
            @Email (message = "inválido")
            String email,
            @NotBlank(message = "Campo obrigatório")
            String senha,
            List<String> roles
) {
}
