package com.example.demo.controlador;

import com.example.demo.dto.LoginPeticion;
import com.example.demo.dto.UsuarioRespuesta;
import com.example.demo.servicio.UsuarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControlador {

    private final UsuarioServicio usuarioServicio;

    @PostMapping("/login")
    public UsuarioRespuesta login(@Valid @RequestBody LoginPeticion peticion) {
        return usuarioServicio.login(peticion.getCorreoElectronico(), peticion.getContrasena());
    }
}
