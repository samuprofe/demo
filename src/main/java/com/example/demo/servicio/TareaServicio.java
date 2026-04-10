package com.example.demo.servicio;

import com.example.demo.dto.TareaPeticion;
import com.example.demo.dto.TareaRespuesta;
import com.example.demo.modelo.entidad.Tarea;
import com.example.demo.modelo.entidad.Usuario;
import com.example.demo.modelo.repositorio.TareaRepositorio;
import com.example.demo.modelo.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TareaServicio {

    private final TareaRepositorio tareaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    @Transactional(readOnly = true)
    public List<TareaRespuesta> listarTodas() {
        return tareaRepositorio.findAll().stream()
                .map(this::toRespuesta)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TareaRespuesta> listarPorUsuario(Long usuarioId) {
        buscarUsuario(usuarioId);
        return tareaRepositorio.findByPropietarioId(usuarioId).stream()
                .map(this::toRespuesta)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TareaRespuesta> listarPorUsuarioYCompletada(Long usuarioId, boolean completada) {
        buscarUsuario(usuarioId);
        return tareaRepositorio.findByPropietarioIdAndCompletada(usuarioId, completada).stream()
                .map(this::toRespuesta)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TareaRespuesta> listarPorUsuarioYFecha(Long usuarioId, LocalDate desde, LocalDate hasta) {
        buscarUsuario(usuarioId);
        return tareaRepositorio.findByPropietarioIdAndFechaLimiteBetween(usuarioId, desde, hasta).stream()
                .map(this::toRespuesta)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TareaRespuesta> listarPorCompletada(boolean completada) {
        return tareaRepositorio.findByCompletada(completada).stream()
                .map(this::toRespuesta)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TareaRespuesta obtenerPorId(Long id) {
        return toRespuesta(buscarPorId(id));
    }

    public TareaRespuesta crear(TareaPeticion peticion) {
        Usuario propietario = buscarUsuario(peticion.getPropietarioId());
        Tarea tarea = new Tarea();
        tarea.setTitulo(peticion.getTitulo());
        tarea.setTexto(peticion.getTexto());
        tarea.setFechaLimite(peticion.getFechaLimite());
        tarea.setCompletada(false);
        tarea.setPropietario(propietario);
        return toRespuesta(tareaRepositorio.save(tarea));
    }

    public TareaRespuesta actualizar(Long id, TareaPeticion peticion) {
        Tarea tarea = buscarPorId(id);
        tarea.setTitulo(peticion.getTitulo());
        tarea.setTexto(peticion.getTexto());
        tarea.setFechaLimite(peticion.getFechaLimite());
        if (!tarea.getPropietario().getId().equals(peticion.getPropietarioId())) {
            tarea.setPropietario(buscarUsuario(peticion.getPropietarioId()));
        }
        return toRespuesta(tareaRepositorio.save(tarea));
    }

    public TareaRespuesta completar(Long id) {
        Tarea tarea = buscarPorId(id);
        if (!tarea.isCompletada()) {
            tarea.setCompletada(true);
            tarea.setCompletadaEn(LocalDateTime.now());
        }
        return toRespuesta(tareaRepositorio.save(tarea));
    }

    public TareaRespuesta descompletar(Long id) {
        Tarea tarea = buscarPorId(id);
        if (tarea.isCompletada()) {
            tarea.setCompletada(false);
            tarea.setCompletadaEn(null);
        }
        return toRespuesta(tareaRepositorio.save(tarea));
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        tareaRepositorio.deleteById(id);
    }

    private Tarea buscarPorId(Long id) {
        return tareaRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Tarea no encontrada con id: " + id));
    }

    private Usuario buscarUsuario(Long usuarioId) {
        return usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con id: " + usuarioId));
    }

    private TareaRespuesta toRespuesta(Tarea tarea) {
        TareaRespuesta respuesta = new TareaRespuesta();
        respuesta.setId(tarea.getId());
        respuesta.setTitulo(tarea.getTitulo());
        respuesta.setTexto(tarea.getTexto());
        respuesta.setFechaLimite(tarea.getFechaLimite());
        respuesta.setCompletada(tarea.isCompletada());
        respuesta.setCompletadaEn(tarea.getCompletadaEn());
        respuesta.setCreadoEn(tarea.getCreadoEn());
        respuesta.setActualizadoEn(tarea.getActualizadoEn());
        respuesta.setPropietarioId(tarea.getPropietario().getId());
        respuesta.setPropietarioNombre(tarea.getPropietario().getNombreCompleto());
        return respuesta;
    }
}
