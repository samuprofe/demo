package com.example.demo.controlador;

import com.example.demo.dto.UsuarioPeticion;
import com.example.demo.dto.UsuarioRespuesta;
import com.example.demo.servicio.UsuarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    @GetMapping
    public List<UsuarioRespuesta> listarTodos() {
        return usuarioServicio.listarTodos();
    }

    @GetMapping("/activos")
    public List<UsuarioRespuesta> listarActivos() {
        return usuarioServicio.listarActivos();
    }

    @GetMapping("/{id}")
    public UsuarioRespuesta obtenerPorId(@PathVariable Long id) {
        return usuarioServicio.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioRespuesta crear(@Valid @RequestBody UsuarioPeticion peticion) {
        return usuarioServicio.crear(peticion);
    }

    @PutMapping("/{id}")
    public UsuarioRespuesta actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioPeticion peticion) {
        return usuarioServicio.actualizar(id, peticion);
    }

    @PatchMapping("/{id}/habilitar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void habilitar(@PathVariable Long id) {
        usuarioServicio.habilitar(id);
    }

    @PatchMapping("/{id}/deshabilitar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deshabilitar(@PathVariable Long id) {
        usuarioServicio.deshabilitar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        usuarioServicio.eliminar(id);
    }
}
