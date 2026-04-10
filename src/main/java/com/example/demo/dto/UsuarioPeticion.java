package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UsuarioPeticion {

    @NotBlank
    @Email
    @Size(max = 120)
    private String correoElectronico;

    @NotBlank
    @Size(max = 120)
    private String nombreCompleto;

    @NotBlank
    @Size(min = 6, max = 255)
    private String contrasena;

    private boolean habilitado = true;

    private Set<String> roles;
}
