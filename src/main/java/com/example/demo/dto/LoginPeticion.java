package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginPeticion {

    @NotBlank
    @Email
    private String correoElectronico;

    @NotBlank
    private String contrasena;
}
