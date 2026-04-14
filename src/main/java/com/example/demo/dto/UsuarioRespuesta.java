package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UsuarioRespuesta {

    private Long id;
    private String correoElectronico;
    private String nombreCompleto;
    private boolean habilitado;
    private LocalDateTime ultimoAccesoEn;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
    private Set<String> roles;
    private boolean tieneFoto;
}
