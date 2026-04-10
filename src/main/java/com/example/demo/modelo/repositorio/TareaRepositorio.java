package com.example.demo.modelo.repositorio;

import com.example.demo.modelo.entidad.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TareaRepositorio extends JpaRepository<Tarea, Long> {

    List<Tarea> findByPropietarioId(Long propietarioId);

    List<Tarea> findByPropietarioIdAndCompletada(Long propietarioId, boolean completada);

    List<Tarea> findByPropietarioIdAndFechaLimiteBetween(Long propietarioId, LocalDate fechaDesde, LocalDate fechaHasta);

    List<Tarea> findByCompletada(boolean completada);
}

