package com.example.demo.configuracion;

import com.example.demo.modelo.entidad.Rol;
import com.example.demo.modelo.entidad.RolNombre;
import com.example.demo.modelo.entidad.Tarea;
import com.example.demo.modelo.entidad.Usuario;
import com.example.demo.modelo.repositorio.RolRepositorio;
import com.example.demo.modelo.repositorio.TareaRepositorio;
import com.example.demo.modelo.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepositorio rolRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final TareaRepositorio tareaRepositorio;

    @Override
    @Transactional
    public void run(String... args) {
        Rol rolAdmin = obtenerOCrearRol(RolNombre.ADMIN);
        Rol rolUser = obtenerOCrearRol(RolNombre.USER);

        Usuario admin = obtenerOCrearUsuario(
                "admin@demo.com",
                "Administrador Demo",
                "admin123",
                true,
                Set.of(rolAdmin, rolUser)
        );

        Usuario user1 = obtenerOCrearUsuario(
                "ana@demo.com",
                "Ana Garcia",
                "ana12345",
                true,
                Set.of(rolUser)
        );

        Usuario user2 = obtenerOCrearUsuario(
                "luis@demo.com",
                "Luis Perez",
                "luis12345",
                true,
                Set.of(rolUser)
        );

        crearTareasSiNoTiene(admin, List.of(
                tarea("Revisar panel de administracion", "Comprobar usuarios activos y tareas vencidas", LocalDate.now().plusDays(1), false),
                tarea("Preparar reporte semanal", "Generar resumen para el equipo", LocalDate.now().plusDays(3), true),
                tarea("Auditar tareas pendientes", "Validar tareas criticas no completadas", LocalDate.now().plusDays(5), false)
        ));

        crearTareasSiNoTiene(user1, List.of(
                tarea("Comprar material", "Comprar cuadernos y boligrafos", LocalDate.now().plusDays(2), false),
                tarea("Enviar correo al cliente", "Responder dudas del cliente principal", LocalDate.now().plusDays(1), true),
                tarea("Actualizar perfil", "Revisar datos de contacto", LocalDate.now().plusDays(7), false)
        ));

        crearTareasSiNoTiene(user2, List.of(
                tarea("Planificar sprint", "Definir objetivos de la semana", LocalDate.now().plusDays(2), false),
                tarea("Corregir bug de login", "Validar flujo de autenticacion", LocalDate.now().plusDays(1), true),
                tarea("Documentar endpoints", "Actualizar README con API REST", LocalDate.now().plusDays(4), false)
        ));
    }

    private Rol obtenerOCrearRol(RolNombre nombre) {
        return rolRepositorio.findByNombre(nombre)
                .orElseGet(() -> rolRepositorio.save(Rol.builder().nombre(nombre).build()));
    }

    private Usuario obtenerOCrearUsuario(
            String correo,
            String nombreCompleto,
            String contrasena,
            boolean habilitado,
            Set<Rol> roles) {

        return usuarioRepositorio.findByCorreoElectronico(correo)
                .map(existente -> {
                    existente.setNombreCompleto(nombreCompleto);
                    existente.setContrasenaHash(contrasena);
                    existente.setHabilitado(habilitado);
                    existente.setRoles(new HashSet<>(roles));
                    return usuarioRepositorio.save(existente);
                })
                .orElseGet(() -> usuarioRepositorio.save(
                        Usuario.builder()
                                .correoElectronico(correo)
                                .nombreCompleto(nombreCompleto)
                                .contrasenaHash(contrasena)
                                .habilitado(habilitado)
                                .roles(new HashSet<>(roles))
                                .build()
                ));
    }

    private void crearTareasSiNoTiene(Usuario propietario, List<Tarea> tareasBase) {
        if (!tareaRepositorio.findByPropietarioId(propietario.getId()).isEmpty()) {
            return;
        }

        List<Tarea> tareas = new ArrayList<>();
        for (Tarea base : tareasBase) {
            base.setPropietario(propietario);
            if (base.isCompletada()) {
                base.setCompletadaEn(LocalDateTime.now());
            }
            tareas.add(base);
        }
        tareaRepositorio.saveAll(tareas);
    }

    private Tarea tarea(String titulo, String texto, LocalDate fechaLimite, boolean completada) {
        return Tarea.builder()
                .titulo(titulo)
                .texto(texto)
                .fechaLimite(fechaLimite)
                .completada(completada)
                .build();
    }
}

