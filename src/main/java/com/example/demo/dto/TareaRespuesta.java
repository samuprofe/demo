package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TareaRespuesta {

    private Long id;
    private String titulo;
    private String texto;
    private LocalDate fechaLimite;
    private boolean completada;
    private LocalDateTime completadaEn;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
    private Long propietarioId;
    private String propietarioNombre;
}
