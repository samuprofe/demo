package com.example.demo.servicio;

import com.example.demo.dto.UsuarioPeticion;
import com.example.demo.dto.UsuarioRespuesta;
import com.example.demo.modelo.entidad.Rol;
import com.example.demo.modelo.entidad.RolNombre;
import com.example.demo.modelo.entidad.Usuario;
import com.example.demo.modelo.repositorio.RolRepositorio;
import com.example.demo.modelo.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final RolRepositorio rolRepositorio;

    @Transactional(readOnly = true)
    public List<UsuarioRespuesta> listarTodos() {
        return usuarioRepositorio.findAll().stream()
                .map(this::toRespuesta)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioRespuesta> listarActivos() {
        return usuarioRepositorio.findByHabilitadoTrue().stream()
                .map(this::toRespuesta)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioRespuesta obtenerPorId(Long id) {
        return toRespuesta(buscarPorId(id));
    }

    @Transactional(readOnly = true)
    public UsuarioRespuesta obtenerPorCorreo(String correoElectronico) {
        Usuario usuario = usuarioRepositorio.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con correo: " + correoElectronico));
        return toRespuesta(usuario);
    }

    public UsuarioRespuesta crear(UsuarioPeticion peticion) {
        if (usuarioRepositorio.existsByCorreoElectronico(peticion.getCorreoElectronico())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un usuario con el correo: " + peticion.getCorreoElectronico());
        }
        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico(peticion.getCorreoElectronico());
        usuario.setNombreCompleto(peticion.getNombreCompleto());
        // TODO: reemplazar por BCrypt cuando se integre Spring Security
        usuario.setContrasenaHash(peticion.getContrasena());
        usuario.setHabilitado(peticion.isHabilitado());
        usuario.setRoles(resolverRoles(peticion.getRoles()));
        return toRespuesta(usuarioRepositorio.save(usuario));
    }

    public UsuarioRespuesta actualizar(Long id, UsuarioPeticion peticion) {
        Usuario usuario = buscarPorId(id);
        if (!usuario.getCorreoElectronico().equals(peticion.getCorreoElectronico())
                && usuarioRepositorio.existsByCorreoElectronico(peticion.getCorreoElectronico())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un usuario con el correo: " + peticion.getCorreoElectronico());
        }
        usuario.setCorreoElectronico(peticion.getCorreoElectronico());
        usuario.setNombreCompleto(peticion.getNombreCompleto());
        if (peticion.getContrasena() != null && !peticion.getContrasena().isBlank()) {
            // TODO: reemplazar por BCrypt cuando se integre Spring Security
            usuario.setContrasenaHash(peticion.getContrasena());
        }
        usuario.setHabilitado(peticion.isHabilitado());
        if (peticion.getRoles() != null) {
            usuario.setRoles(resolverRoles(peticion.getRoles()));
        }
        return toRespuesta(usuarioRepositorio.save(usuario));
    }

    public void habilitar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setHabilitado(true);
        usuarioRepositorio.save(usuario);
    }

    public void deshabilitar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setHabilitado(false);
        usuarioRepositorio.save(usuario);
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        usuarioRepositorio.deleteById(id);
    }

    public void subirFoto(Long id, byte[] datos, String tipo) {
        Usuario usuario = buscarPorId(id);
        usuario.setFotoPerfil(datos);
        usuario.setFotoPerfilTipo(tipo);
        usuarioRepositorio.save(usuario);
    }

    @Transactional(readOnly = true)
    public byte[] obtenerFoto(Long id) {
        Usuario usuario = buscarPorId(id);
        if (usuario.getFotoPerfil() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no tiene foto de perfil");
        }
        return usuario.getFotoPerfil();
    }

    @Transactional(readOnly = true)
    public String obtenerFotoTipo(Long id) {
        Usuario usuario = buscarPorId(id);
        String tipo = usuario.getFotoPerfilTipo();
        return (tipo != null && !tipo.isBlank()) ? tipo : "image/jpeg";
    }

    @Transactional(readOnly = true)
    public UsuarioRespuesta login(String correoElectronico, String contrasena) {
        Usuario usuario = usuarioRepositorio.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"));
        if (!usuario.isHabilitado()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario deshabilitado");
        }
        // TODO: reemplazar por BCrypt cuando se integre Spring Security
        if (!usuario.getContrasenaHash().equals(contrasena)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");
        }
        return toRespuesta(usuario);
    }

    private Usuario buscarPorId(Long id) {
        return usuarioRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con id: " + id));
    }

    private Set<Rol> resolverRoles(Set<String> nombres) {
        Set<Rol> roles = new HashSet<>();
        if (nombres == null || nombres.isEmpty()) {
            rolRepositorio.findByNombre(RolNombre.USER).ifPresent(roles::add);
            return roles;
        }
        for (String nombre : nombres) {
            RolNombre rolNombre;
            try {
                rolNombre = RolNombre.valueOf(nombre.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol no válido: " + nombre);
            }
            Rol rol = rolRepositorio.findByNombre(rolNombre)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Rol no encontrado: " + nombre));
            roles.add(rol);
        }
        return roles;
    }

    UsuarioRespuesta toRespuesta(Usuario usuario) {
        UsuarioRespuesta respuesta = new UsuarioRespuesta();
        respuesta.setId(usuario.getId());
        respuesta.setCorreoElectronico(usuario.getCorreoElectronico());
        respuesta.setNombreCompleto(usuario.getNombreCompleto());
        respuesta.setHabilitado(usuario.isHabilitado());
        respuesta.setUltimoAccesoEn(usuario.getUltimoAccesoEn());
        respuesta.setCreadoEn(usuario.getCreadoEn());
        respuesta.setActualizadoEn(usuario.getActualizadoEn());
        respuesta.setRoles(usuario.getRoles().stream()
                .map(r -> r.getNombre().name())
                .collect(Collectors.toSet()));
        respuesta.setTieneFoto(usuario.getFotoPerfil() != null);
        return respuesta;
    }
}
