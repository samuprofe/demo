package com.example.demo.controlador;

import com.example.demo.dto.TareaPeticion;
import com.example.demo.dto.TareaRespuesta;
import com.example.demo.servicio.TareaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaControlador {

    private final TareaServicio tareaServicio;

    @GetMapping
    public List<TareaRespuesta> listarTodas(
            @RequestParam(required = false) Boolean completada) {
        if (completada != null) {
            return tareaServicio.listarPorCompletada(completada);
        }
        return tareaServicio.listarTodas();
    }

    @GetMapping("/{id}")
    public TareaRespuesta obtenerPorId(@PathVariable Long id) {
        return tareaServicio.obtenerPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<TareaRespuesta> listarPorUsuario(
            @PathVariable Long usuarioId,
            @RequestParam(required = false) Boolean completada,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        if (completada != null) {
            return tareaServicio.listarPorUsuarioYCompletada(usuarioId, completada);
        }
        if (desde != null && hasta != null) {
            return tareaServicio.listarPorUsuarioYFecha(usuarioId, desde, hasta);
        }
        return tareaServicio.listarPorUsuario(usuarioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TareaRespuesta crear(@Valid @RequestBody TareaPeticion peticion) {
        return tareaServicio.crear(peticion);
    }

    @PutMapping("/{id}")
    public TareaRespuesta actualizar(@PathVariable Long id, @Valid @RequestBody TareaPeticion peticion) {
        return tareaServicio.actualizar(id, peticion);
    }

    @PatchMapping("/{id}/completar")
    public TareaRespuesta completar(@PathVariable Long id) {
        return tareaServicio.completar(id);
    }

    @PatchMapping("/{id}/descompletar")
    public TareaRespuesta descompletar(@PathVariable Long id) {
        return tareaServicio.descompletar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        tareaServicio.eliminar(id);
    }
}
